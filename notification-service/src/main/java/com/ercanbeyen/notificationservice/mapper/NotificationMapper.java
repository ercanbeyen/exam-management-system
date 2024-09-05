package com.ercanbeyen.notificationservice.mapper;

import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import com.ercanbeyen.notificationservice.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDto entityToDto(Notification notification);
    Notification dtoToEntity(NotificationDto notificationDto);
}
