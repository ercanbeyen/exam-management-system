package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.service.ExamRegistrationNotificationService;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import com.ercanbeyen.servicecommon.client.messaging.ChannelTopic;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExamRegistrationNotificationServiceImpl implements ExamRegistrationNotificationService {
    private final StreamBridge streamBridge;

    @Override
    public void sendToQueue(ExamRegistrationDto examRegistrationDto) {
        String message = String.format("Candidate %s is successfully registered to exam event %s", examRegistrationDto.candidateId(), examRegistrationDto.examEventId());
        NotificationDto notificationDto = new NotificationDto(message, LocalDateTime.now());

        streamBridge.send(ChannelTopic.PRODUCER_BINDING_IN_0, notificationDto);
        log.info(LogMessage.NOTIFICATION_SENT_TO_QUEUE, ChannelTopic.PRODUCER_BINDING_IN_0, LocalDateTime.now());
    }
}
