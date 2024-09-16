package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.entity.ExamEvent;

import java.util.List;

public interface ExamEventService {
    ExamEventDto createExamEvent(ExamEventDto request);
    ExamEventDto updateExamEvent(String id, ExamEventDto request);
    ExamEventDto getExamEvent(String id);
    List<ExamEventDto> getExamEvents();
    String deleteExamEvent(String id);
    ExamEvent findById(String id);
}
