package com.ercanbeyen.notificationservice.config;

import com.ercanbeyen.notificationservice.service.NotificationService;
import com.ercanbeyen.notificationservice.client.AuthClient;
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
    private final AuthClient authClient;

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable("id") String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(notificationService.getNotification(id, username));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam(value = "user") String notificationUsername, @RequestHeader("loggedInUser") String loggedInUsername) {
        authClient.checkLoggedInUser(notificationUsername, loggedInUsername);
        return ResponseEntity.ok(notificationService.getNotifications(notificationUsername));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(notificationService.deleteNotification(id, username));
    }
}
