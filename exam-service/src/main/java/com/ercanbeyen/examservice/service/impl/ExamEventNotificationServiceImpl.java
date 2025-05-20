package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventNotificationService;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
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
public class ExamEventNotificationServiceImpl implements ExamEventNotificationService {
    private final StreamBridge streamBridge;

    @Override
    public void sendToQueue(ExamEventDto examEventDto, String username) {
        String message = String.format("Exam event %s is for exam %s", examEventDto.id(), examEventDto.examSubject());
        NotificationDto notificationDto = new NotificationDto(username, message, LocalDateTime.now());

        streamBridge.send(ChannelTopic.PRODUCER_BINDING_IN_0, notificationDto);
        log.info(LogMessage.NOTIFICATION_SENT_TO_QUEUE, ChannelTopic.PRODUCER_BINDING_IN_0, LocalDateTime.now());
    }
}
