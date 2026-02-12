package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.dto.response.ExamEntry;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExamRegistrationService {
    ExamRegistrationDto createExamRegistration(ExamRegistrationDto request, String username);
    ExamRegistrationDto updateExamRegistration(String id, ExamRegistrationDto request, String username);
    ExamRegistrationDto getExamRegistration(String id, String username);
    Page<ExamRegistrationDto> getExamRegistrations(String username, int pageNumber, int pageSize);
    List<ExamEntry> getExamEntries(String examEventId, String username);
    String deleteExamRegistration(String id, String username);
    ExamRegistration findById(String id);
}
