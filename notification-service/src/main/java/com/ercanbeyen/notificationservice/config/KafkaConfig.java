package com.ercanbeyen.notificationservice.config;

import com.ercanbeyen.notificationservice.service.NotificationService;
import com.ercanbeyen.servicecommon.client.exception.InternalServerErrorException;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {
    private final NotificationService notificationService;

    @Bean
    public Function<NotificationDto, NotificationDto> producerBinding() {
        return request -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException exception) {
                log.error("Threw Exception: {}", exception.getClass());
                Thread.currentThread().interrupt();
                throw new InternalServerErrorException(exception.getMessage());
            } catch (Exception exception) {
                log.error("Unknown exception: {}", exception.getClass());
                Thread.currentThread().interrupt();
                throw new InternalServerErrorException(exception.getMessage());
            }

            log.info("Process is in producer stage");
            return request;
        };
    }

    @Bean
    public Function<NotificationDto, NotificationDto> processorBinding() {
        return request -> {
            NotificationDto notificationDto = new NotificationDto(request.message(), LocalDateTime.now());
            log.info("Process is in processor stage");
            return notificationDto;
        };
    }

    @Bean
    public Consumer<NotificationDto> consumerBinding() {
        return notificationDto -> {
            log.info("Process is in consumer stage");
            NotificationDto createdNotification = notificationService.createNotification(notificationDto);
            log.info("Notification is created at {}", createdNotification.createdAt());
        };
    }
}
