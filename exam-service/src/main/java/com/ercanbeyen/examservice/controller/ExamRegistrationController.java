package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-registrations")
@RequiredArgsConstructor
public class ExamRegistrationController {
    private final ExamRegistrationService examRegistrationService;

    @PostMapping
    public ResponseEntity<ExamRegistrationDto> createExamRegistration(@RequestBody ExamRegistrationDto request) {
        return ResponseEntity.ok(examRegistrationService.createExamRegistration(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> updateExamRegistration(@PathVariable String id, @RequestBody ExamRegistrationDto request) {
        return ResponseEntity.ok(examRegistrationService.updateExamRegistration(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> getExamRegistration(@PathVariable String id) {
        return ResponseEntity.ok(examRegistrationService.getExamRegistration(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamRegistrationDto>> getExamRegistrations() {
        return ResponseEntity.ok(examRegistrationService.getExamRegistrations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamRegistration(@PathVariable String id) {
        return ResponseEntity.ok(examRegistrationService.deleteExamRegistration(id));
    }
}
