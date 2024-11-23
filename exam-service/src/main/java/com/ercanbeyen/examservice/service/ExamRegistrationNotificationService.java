package com.ercanbeyen.examservice.service;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;

public interface ExamRegistrationNotificationService {
    void sendToQueue(ExamRegistrationDto examRegistrationDto);
}
