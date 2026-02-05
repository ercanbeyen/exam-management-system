package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.entity.ExamResult;
import com.ercanbeyen.examservice.mapper.ExamResultMapper;
import com.ercanbeyen.examservice.repository.ExamResultRepository;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.service.ExamResultService;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamResultServiceImpl implements ExamResultService {
    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final ExamRegistrationService examRegistrationService;

    @Override
    public ExamResultDto createExamResult(ExamResultDto request) {
        ExamResult examResult = examResultMapper.dtoToEntity(request);
        ExamRegistration examRegistration = examRegistrationService.findById(request.examRegistrationId());
        examResult.setExamRegistration(examRegistration);
        return examResultMapper.entityToDto(examResultRepository.save(examResult));
    }

    @Override
    public ExamResultDto updateExamResult(String id, ExamResultDto request) {
        ExamResult examResult = findById(id);
        ExamRegistration examRegistration = examRegistrationService.findById(request.examRegistrationId());

        examResult.setCandidateId(request.candidateId());
        examResult.setScore(request.score());
        examResult.setExamRegistration(examRegistration);

        return examResultMapper.entityToDto(examResultRepository.save(examResult));
    }

    @Override
    public ExamResultDto getExamResult(String id) {
        return examResultMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamResultDto> getExamResults() {
        return examResultRepository.findAll()
                .stream()
                .map(examResultMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteExamResult(String id) {
        ExamResult examResult = findById(id);
        examResultRepository.delete(examResult);
        return "Exam result is successfully deleted";
    }

    private ExamResult findById(String id) {
        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam result is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "Exam result", id);

        return examResult;
    }
}
