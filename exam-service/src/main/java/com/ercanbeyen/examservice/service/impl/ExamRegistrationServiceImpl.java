package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.mapper.ExamRegistrationMapper;
import com.ercanbeyen.examservice.repository.ExamRegistrationRepository;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamRegistrationNotificationService;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.servicecommon.client.CandidateServiceClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final CandidateServiceClient candidateServiceClient;
    private final ExamRegistrationNotificationService examRegistrationNotificationService;

    @Override
    public ExamRegistrationDto createExamRegistration(ExamRegistrationDto request) {
        ExamRegistration examRegistration = constructExamRegistration(null, request);
        ExamRegistration savedExamRegistration = examRegistrationRepository.save(examRegistration);

        ExamRegistrationDto examRegistrationDto = examRegistrationMapper.entityToDto(savedExamRegistration);
        examRegistrationNotificationService.sendToQueue(examRegistrationDto);

        return examRegistrationDto;
    }

    @Override
    public ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request) {
        ExamRegistration examRegistration = constructExamRegistration(id, request);
        return examRegistrationMapper.entityToDto(examRegistrationRepository.save(examRegistration));
    }

    @Override
    public ExamRegistrationDto getExamRegistration(String id) {
        return examRegistrationMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamRegistrationDto> getExamRegistrations() {
        return examRegistrationRepository.findAll()
                .stream()
                .map(examRegistrationMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteExamRegistration(String id) {
        ExamRegistration examRegistration = findById(id);
        examRegistrationRepository.delete(examRegistration);
        return String.format("Exam registration %s is successfully deleted", id);
    }

    private ExamRegistration findById(String id) {
        ExamRegistration examRegistration = examRegistrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Exam registration %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Exam registration", id);

        return examRegistration;
    }

    private ExamRegistration constructExamRegistration(String id, ExamRegistrationDto request) {
        ExamRegistration examRegistration = Optional.ofNullable(id).isPresent()
                ? findById(id)
                : examRegistrationMapper.dtoToEntity(request);

        ExamEvent examEvent = examEventService.findById(request.examEventId());

        ResponseEntity<CandidateDto> candidateServiceResponse = candidateServiceClient.getCandidate(request.candidateId());
        log.info(LogMessage.CLIENT_SERVICE_RESPONSE, "Candidate", candidateServiceResponse);

        examRegistration.setExamEvent(examEvent);
        examRegistration.setCandidateId(request.candidateId());

        return examRegistration;
    }
}
