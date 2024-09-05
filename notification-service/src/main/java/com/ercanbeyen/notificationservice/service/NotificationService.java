package com.ercanbeyen.notificationservice.service;

import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto request);
    NotificationDto getNotification(String id);
    List<NotificationDto> getNotifications();
    String deleteNotification(String id);
}
