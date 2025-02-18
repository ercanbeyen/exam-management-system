package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.entity.Exam;

import java.util.List;

public interface ExamService {
    ExamDto createExam(ExamDto request);
    ExamDto updateExam(String id, ExamDto request);
    ExamDto getExam(String id);
    List<ExamDto> getExams();
    String deleteExam(String id);
    Exam findById(String id);
    Exam findBySubject(String subject);
}
