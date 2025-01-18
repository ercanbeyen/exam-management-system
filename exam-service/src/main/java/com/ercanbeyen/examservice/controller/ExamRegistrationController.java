package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-registrations")
@RequiredArgsConstructor
public class ExamRegistrationController {
    private final ExamRegistrationService examRegistrationService;
    private final AuthUtil authUtil;

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
    public ResponseEntity<List<ExamRegistrationDto>> getExamRegistrations(@RequestHeader("loggedInUser") String username) {
        authUtil.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examRegistrationService.getExamRegistrations(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamRegistration(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.deleteExamRegistration(id, username));
    }
}
