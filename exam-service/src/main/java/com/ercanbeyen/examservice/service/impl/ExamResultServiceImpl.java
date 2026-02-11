package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.client.CandidateClient;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamResultServiceImpl implements ExamResultService {
    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final ExamRegistrationService examRegistrationService;
    private final CandidateClient candidateClient;

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
    public ExamResultDto getExamResult(String id, String username) {
        ExamResult examResult = findById(id);
        candidateClient.checkCandidate(examResult.getCandidateId(), username);
        return examResultMapper.entityToDto(examResult);
    }

    @Override
    public List<ExamResultDto> getExamResults(String subject) {
        return examResultRepository.findAll()
                .stream()
                .filter(examResult -> examResult.getExamRegistration().getExamEvent().getExam().getSubject().equals(subject))
                .map(examResultMapper::entityToDto)
                .toList();
    }

    @Override
    public Page<ExamResultDto> getExamResultsOfCandidate(String candidateId, int pageNumber, int pageSize) {
        Sort sort = Sort.by("score")
                .descending()
                .and(Sort.by("candidateId").ascending());
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return examResultRepository.findAllByCandidateId(candidateId, pageable).map(examResultMapper::entityToDto);
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
