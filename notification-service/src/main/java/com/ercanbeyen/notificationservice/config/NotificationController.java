package com.ercanbeyen.notificationservice.config;

import com.ercanbeyen.notificationservice.service.NotificationService;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable("id") String id) {
        return ResponseEntity.ok(notificationService.getNotification(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") String id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }
}
