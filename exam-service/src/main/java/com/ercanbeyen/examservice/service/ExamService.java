package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamDto;

import java.util.List;

public interface ExamService {
    ExamDto createExam(ExamDto request);
    ExamDto updateExam(String id, ExamDto request);
    ExamDto getExam(String id);
    List<ExamDto> getExams();
    String deleteExam(String id);
}
