package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.mapper.ExamRegistrationMapper;
import com.ercanbeyen.examservice.repository.ExamRegistrationRepository;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.servicecommon.client.CandidateServiceClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamRegistrationServiceImpl implements ExamRegistrationService {
    private final ExamRegistrationRepository examRegistrationRepository;
    private final ExamRegistrationMapper examRegistrationMapper;
    private final ExamEventService examEventService;
    private final CandidateServiceClient candidateServiceClient;

    @Override
    public ExamRegistrationDto createExamRegistration(ExamRegistrationDto request) {
        ExamRegistration examRegistration = constructExamRegistration(null, request);
        return examRegistrationMapper.entityToDto(examRegistrationRepository.save(examRegistration));
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
        List<ExamRegistrationDto> examRegistrationDtos = new ArrayList<>();

        examRegistrationRepository.findAll()
                .forEach(examRegistration -> examRegistrationDtos.add(examRegistrationMapper.entityToDto(examRegistration)));

        return examRegistrationDtos;
    }

    @Override
    public String deleteExamRegistration(String id) {
        ExamRegistration examRegistration = findById(id);
        examRegistrationRepository.delete(examRegistration);
        return String.format("Exam registration %s is successfully deleted", id);
    }

    private ExamRegistration findById(String id) {
        ExamRegistration examRegistration = examRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Exam registration %s is not found", id)));

        log.info("Exam registration {} is found", id);

        return examRegistration;
    }

    private ExamRegistration constructExamRegistration(String id, ExamRegistrationDto request) {
        ExamRegistration examRegistration;

        if (Optional.ofNullable(id).isPresent()) {
            examRegistration = findById(id);
        } else {
            examRegistration = examRegistrationMapper.dtoToEntity(request);
        }

        ExamEvent examEvent = examEventService.findById(request.examEventId());

        ResponseEntity<CandidateDto> candidateResponse = candidateServiceClient.getCandidate(request.candidateId());
        log.info("Candidate Response: {}", candidateResponse);

        if (Optional.ofNullable(Objects.requireNonNull(candidateResponse.getBody()).id()).isEmpty()) {
            log.error("Fallback method of getStudent has worked. Candidate id is {}", candidateResponse.getBody().id()); // candidate id must be null
            throw new RuntimeException(String.format("Candidate %s is not found", request.candidateId()));
        }

        examRegistration.setExamEvent(examEvent);
        examRegistration.setCandidateId(request.candidateId());

        return examRegistration;
    }
}
