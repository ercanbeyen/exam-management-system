package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamResultDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExamResultService {
    ExamResultDto createExamResult(ExamResultDto request);
    ExamResultDto updateExamResult(String id, ExamResultDto request);
    ExamResultDto getExamResult(String id, String username);
    List<ExamResultDto> getExamResults(String subject);
    Page<ExamResultDto> getExamResultsOfCandidate(String candidateId, int pageNumber, int pageSize);
    String deleteExamResult(String id);
}
