package com.ercanbeyen.notificationservice.service.impl;

import com.ercanbeyen.notificationservice.client.AuthClient;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import com.ercanbeyen.notificationservice.entity.Notification;
import com.ercanbeyen.notificationservice.mapper.NotificationMapper;
import com.ercanbeyen.notificationservice.repository.NotificationRepository;
import com.ercanbeyen.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final AuthClient authClient;

    @Override
    public NotificationDto createNotification(NotificationDto request) {
        Notification notification = notificationMapper.dtoToEntity(request);
        Notification saved = notificationRepository.save(notification);

        return notificationMapper.entityToDto(saved);
    }

    @Override
    public NotificationDto getNotification(String id, String username) {
        Notification notification = findById(id);
        authClient.checkLoggedInUser(notification.getUsername(), username);

        return notificationMapper.entityToDto(notification);
    }

    @Override
    public List<NotificationDto> getNotifications(String username) {
        return notificationRepository.findAllByUsername(username)
                .stream()
                .map(notificationMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteNotification(String id, String username) {
        Notification notification = findById(id);
        authClient.checkLoggedInUser(notification.getUsername(), username);

        notificationRepository.delete(notification);

        return String.format("Notification %s is successfully deleted", id);
    }

    private Notification findById(String id) {
        Notification candidate = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Notification %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Notification", id);

        return candidate;
    }
}
