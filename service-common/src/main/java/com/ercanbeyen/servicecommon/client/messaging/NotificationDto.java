package com.ercanbeyen.servicecommon.client.messaging;

import java.time.LocalDateTime;

public record NotificationDto(String message, LocalDateTime createdAt) {

}