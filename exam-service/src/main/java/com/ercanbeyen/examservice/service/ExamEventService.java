package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.entity.ExamEvent;

import java.util.List;

public interface ExamEventService {
    ExamEventDto createExamEvent(ExamEventDto request, String username);
    ExamEventDto updateExamEvent(String id, ExamEventDto request, String username);
    ExamEventDto getExamEvent(String id, String username);
    List<ExamEventDto> getExamEvents(String username);
    String deleteExamEvent(String id, String username);
    ExamEvent findById(String id);
}
