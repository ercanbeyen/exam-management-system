package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamEventDto;

public interface ExamEventNotificationService {
    void sendToQueue(ExamEventDto examEventDto, String username);
}
