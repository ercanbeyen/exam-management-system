package com.ercanbeyen.notificationservice.service.impl;

import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import com.ercanbeyen.notificationservice.entity.Notification;
import com.ercanbeyen.notificationservice.mapper.NotificationMapper;
import com.ercanbeyen.notificationservice.repository.NotificationRepository;
import com.ercanbeyen.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationDto createNotification(NotificationDto request) {
       Notification notification = notificationMapper.dtoToEntity(request);
       return notificationMapper.entityToDto(notificationRepository.save(notification));
    }

    @Override
    public NotificationDto getNotification(String id) {
        Notification notification = findById(id);
        return notificationMapper.entityToDto(notification);
    }

    @Override
    public List<NotificationDto> getNotifications() {
        List<NotificationDto> notificationDtos = new ArrayList<>();

        notificationRepository.findAll()
                .forEach(notification -> notificationDtos.add(notificationMapper.entityToDto(notification)));

        log.info("Notifications are successfully fetched");

        return notificationDtos;
    }

    @Override
    public String deleteNotification(String id) {
        Notification notification = findById(id);
        notificationRepository.delete(notification);
        return String.format("Notification %s is successfully deleted", id);
    }

    private Notification findById(String id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Notification %s is not found", id)));
    }
}
