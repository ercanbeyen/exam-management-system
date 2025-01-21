package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.embeddable.RegistrationPeriod;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.exception.TimeExpiredException;
import com.ercanbeyen.examservice.mapper.ExamRegistrationMapper;
import com.ercanbeyen.examservice.repository.ExamRegistrationRepository;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamRegistrationNotificationService;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
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
    private final ExamEventService examEventService;
    private final ExamRegistrationNotificationService examRegistrationNotificationService;
    private final CandidateClient candidateClient;

    @Override
    public ExamRegistrationDto createExamRegistration(ExamRegistrationDto request, String username) {
        ExamEvent examEvent = examEventService.findById(request.examEventId());

        checkExamRegistrationPeriod(examEvent.getExam());

        String candidateId = request.candidateId();
        candidateClient.checkCandidate(username, candidateId);

        if (examRegistrationRepository.existsByExamEventAndCandidateId(examEvent, candidateId)) {
            throw new ResourceConflictException("Candidate has already been registered to exam before");
        }

        log.info("Candidate {} has not registered to exam {} yet", candidateId, examEvent.getExam().getSubject());
        ExamRegistration examRegistration = examRegistrationMapper.dtoToEntity(request);

        examRegistration.setExamEvent(examEvent);
        examRegistration.setCandidateId(request.candidateId());

        ExamRegistration savedExamRegistration = examRegistrationRepository.save(examRegistration);

        ExamRegistrationDto examRegistrationDto = examRegistrationMapper.entityToDto(savedExamRegistration);
        examRegistrationNotificationService.sendToQueue(examRegistrationDto, username);

        return examRegistrationDto;
    }

    @Override
    public ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request, String username) {
        ExamRegistration examRegistration = findById(id);

        checkExamRegistrationPeriod(examRegistration.getExamEvent().getExam());

        ExamEvent examEvent = examEventService.findById(request.examEventId());

        String candidateId = request.candidateId();
        candidateClient.checkCandidate(username, candidateId);

        examRegistration.setExamEvent(examEvent);

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

    private static void checkExamRegistrationPeriod(Exam exam) {
        RegistrationPeriod registrationPeriod = exam.getRegistrationPeriod();
        LocalDateTime now = LocalDateTime.now();

        if (registrationPeriod.getBeginAt().isAfter(now)) {
            throw new TimeExpiredException("Registration period for exam " + exam.getSubject() + " has not been started yet.");
        }

        if (registrationPeriod.getEndAt().isBefore(now)) {
            throw new TimeExpiredException("Registration period for exam " + exam.getSubject() + " has already been ended.");
        }

        log.info("Current time is in registration period for the exam");
    }
}
