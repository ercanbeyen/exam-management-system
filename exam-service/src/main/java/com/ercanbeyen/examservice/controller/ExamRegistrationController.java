package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-registrations")
@RequiredArgsConstructor
public class ExamRegistrationController {
    private final ExamRegistrationService examRegistrationService;
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<ExamRegistrationDto> createExamRegistration(@RequestBody ExamRegistrationDto request, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.createExamRegistration(request, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> updateExamRegistration(@PathVariable String id, @RequestBody ExamRegistrationDto request, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.updateExamRegistration(id, request, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> getExamRegistration(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.getExamRegistration(id, username));
    }

    @GetMapping
    public ResponseEntity<List<ExamRegistrationDto>> getExamRegistrations(@RequestParam("user") String candidateUsername, @RequestHeader("loggedInUser") String loggedInUsername) {
        authClient.checkLoggedInUser(candidateUsername, loggedInUsername);
        return ResponseEntity.ok(examRegistrationService.getExamRegistrations(candidateUsername));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamRegistration(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.deleteExamRegistration(id, username));
    }
}
