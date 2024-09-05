package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventNotificationService;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExamEventNotificationImpl implements ExamEventNotificationService {
    private final StreamBridge streamBridge;
    @Override
    public void sendToQueue(ExamEventDto examEventDto) {
        String message = String.format("Exam event %s is for exam %s", examEventDto.id(), examEventDto.examId());
        NotificationDto notificationDto = new NotificationDto(message, LocalDateTime.now());

        log.info("NotificationDto is successfully constructed");

        streamBridge.send("producerBinding-in-0", notificationDto);

        log.info("NotificationDto is sent to queue at {}", LocalDateTime.now());
    }
}
