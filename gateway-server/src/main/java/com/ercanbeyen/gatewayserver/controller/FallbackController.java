package com.ercanbeyen.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fb")
public class FallbackController {
    @GetMapping("/exam")
    String examFallback() {
        return "Exam Service is unavailable";
    }

    @GetMapping("/school")
    String schoolFallback() {
        return "School Service is unavailable";
    }

    @GetMapping("/candidate")
    String candidateFallback() {
        return "Candidate Service is unavailable";
    }

    @GetMapping("/notification")
    String notificationFallback() {
        return "Notification Service is unavailable";
    }
}
