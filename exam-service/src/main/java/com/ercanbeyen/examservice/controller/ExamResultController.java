package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.service.ExamResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-results")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamResultController {
    private final ExamResultService examResultService;

    @PostMapping
    public ResponseEntity<ExamResultDto> createExamResult(@RequestBody ExamResultDto request) {
        return ResponseEntity.ok(examResultService.createExamResult(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResultDto> updateExamResult(@PathVariable("id") String id, @RequestBody ExamResultDto request) {
        return ResponseEntity.ok(examResultService.updateExamResult(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResultDto> getExamResult(@PathVariable("id") String id) {
        return ResponseEntity.ok(examResultService.getExamResult(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamResultDto>> getExamResults() {
        return ResponseEntity.ok(examResultService.getExamResults());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamResult(@PathVariable("id") String id) {
        return ResponseEntity.ok(examResultService.deleteExamResult(id));
    }
}
