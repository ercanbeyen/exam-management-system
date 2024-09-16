package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;

import java.util.List;

public interface ExamRegistrationService {
    ExamRegistrationDto createExamRegistration(ExamRegistrationDto request);
    ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request);
    ExamRegistrationDto getExamRegistration(String id);
    List<ExamRegistrationDto> getExamRegistrations();
    String deleteExamRegistration(String id);
}
