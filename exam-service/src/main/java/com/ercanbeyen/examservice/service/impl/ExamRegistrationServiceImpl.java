package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.mapper.ExamRegistrationMapper;
import com.ercanbeyen.examservice.repository.ExamRegistrationRepository;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamRegistrationNotificationService;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        ExamRegistration examRegistration = constructExamRegistration(null, request, username);
        ExamRegistration savedExamRegistration = examRegistrationRepository.save(examRegistration);

        ExamRegistrationDto examRegistrationDto = examRegistrationMapper.entityToDto(savedExamRegistration);
        examRegistrationNotificationService.sendToQueue(examRegistrationDto, username);

        return examRegistrationDto;
    }

    @Override
    public ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request, String username) {
        ExamRegistration examRegistration = constructExamRegistration(id, request, username);
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

    private ExamRegistration constructExamRegistration(String id, ExamRegistrationDto request, String username) {
        ExamRegistration examRegistration = Optional.ofNullable(id).isPresent()
                ? findById(id)
                : examRegistrationMapper.dtoToEntity(request);

        ExamEvent examEvent = examEventService.findById(request.examEventId());

        String candidateId = request.candidateId();
        candidateClient.checkCandidate(username, candidateId);

        examRegistration.setExamEvent(examEvent);
        examRegistration.setCandidateId(candidateId);

        return examRegistration;
    }
}
