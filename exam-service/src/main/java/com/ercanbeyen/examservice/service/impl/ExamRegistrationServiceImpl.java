package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.client.SchoolClient;
import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.mapper.ExamRegistrationMapper;
import com.ercanbeyen.examservice.repository.ExamRegistrationRepository;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamRegistrationNotificationService;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.examservice.validator.ExamRegistrationValidator;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamRegistrationServiceImpl implements ExamRegistrationService {
    private final ExamRegistrationRepository examRegistrationRepository;
    private final ExamRegistrationMapper examRegistrationMapper;
    private final ExamService examService;
    private final ExamEventService examEventService;
    private final ExamRegistrationNotificationService examRegistrationNotificationService;
    private final CandidateClient candidateClient;
    private final SchoolClient schoolClient;

    @Override
    public ExamRegistrationDto createExamRegistration(ExamRegistrationDto request, String username) {
        ExamEventDto examEventDto = request.examEventDto();
        Exam exam = examService.findBySubject(examEventDto.examSubject());
        ExamEvent examEvent = examEventService.findExamEventBySubjectAndLocationAndPeriod(
                examEventDto.examSubject(), examEventDto.location(), exam.getExamPeriod());

        ExamRegistrationValidator.checkExamRegistrationPeriod(examEvent.getExam());

        String candidateId = request.candidateId();
        candidateClient.checkCandidate(username, candidateId);

        checkIsUserProctorInExam(username, exam);

        if (examRegistrationRepository.existsByExamEventAndCandidateId(examEvent, candidateId)) {
            throw new ResourceConflictException("Candidate has already been registered for exam before");
        }

        log.info("Candidate {} has not registered for exam {} yet", candidateId, examEvent.getExam().getSubject());

        schoolClient.checkClassroomCapacityForExamRegistration(examEvent);

        ExamRegistration examRegistration = examRegistrationMapper.dtoToEntity(request);

        LocalDateTime now = LocalDateTime.now();

        examRegistration.setExamEvent(examEvent);
        examRegistration.setCandidateId(request.candidateId());
        examRegistration.setCreatedAt(now);
        examRegistration.setUpdatedAt(now);

        ExamRegistration savedExamRegistration = examRegistrationRepository.save(examRegistration);

        ExamRegistrationDto examRegistrationDto = examRegistrationMapper.entityToDto(savedExamRegistration);
        examRegistrationNotificationService.sendToQueue(examRegistrationDto, username);

        return examRegistrationDto;
    }

    @Override
    public ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request, String username) {
        ExamRegistration examRegistration = findById(id);
        Exam exam = examRegistration.getExamEvent().getExam();

        ExamRegistrationValidator.checkExamRegistrationPeriod(exam);

        ExamEventDto examEventDto = request.examEventDto();
        ExamEvent examEvent = examEventService.findExamEventBySubjectAndLocationAndPeriod(
                examEventDto.examSubject(), examEventDto.location(), exam.getExamPeriod());

        String candidateId = request.candidateId();
        candidateClient.checkCandidate(username, candidateId);

        if (!examEvent.getId().equals(examRegistration.getExamEvent().getId())) {
            log.info("Classroom of candidate may check. Classroom capacity must be checked before update");
            schoolClient.checkClassroomCapacityForExamRegistration(examEvent);
            examRegistration.setExamEvent(examEvent);
        }

        examRegistration.setUpdatedAt(LocalDateTime.now());

        return examRegistrationMapper.entityToDto(examRegistrationRepository.save(examRegistration));
    }

    @Override
    public ExamRegistrationDto getExamRegistration(String id, String username) {
        ExamRegistration examRegistration = findById(id);
        candidateClient.checkCandidate(username, id);

        return examRegistrationMapper.entityToDto(examRegistration);
    }

    @Override
    public List<ExamRegistrationDto> getExamRegistrations(String username) {
        String candidateId = candidateClient.getCandidateId(username);

        return examRegistrationRepository.findAllByCandidateId(candidateId)
                .stream()
                .map(examRegistrationMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteExamRegistration(String id, String username) {
        ExamRegistration examRegistration = findById(id);
        ExamRegistrationValidator.checkExamRegistrationPeriod(examRegistration.getExamEvent().getExam());
        candidateClient.checkCandidate(username, examRegistration.getCandidateId());

        examRegistrationRepository.delete(examRegistration);
        return String.format("Exam registration %s is successfully deleted", id);
    }

    private ExamRegistration findById(String id) {
        ExamRegistration examRegistration = examRegistrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Exam registration %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Exam registration", id);

        return examRegistration;
    }

    private static void checkIsUserProctorInExam(String username, Exam exam) {
        exam.getExamEvents()
                .stream()
                .map(ExamEvent::getProctors)
                .forEach(proctors -> {
                    for (String proctor : proctors) {
                        if (proctor.equals(username)) {
                            throw new ResourceConflictException("Proctors cannot register for the the exam");
                        }
                    }
                });

        log.info("User {} is not one of the proctors in the exam {}", username, exam.getSubject());
    }
}
