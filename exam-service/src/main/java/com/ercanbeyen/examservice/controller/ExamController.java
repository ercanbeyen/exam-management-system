package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;

    @PostMapping
    public ResponseEntity<ExamDto> createExam(@RequestBody ExamDto request) {
        return ResponseEntity.ok(examService.createExam(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDto> updateExam(@PathVariable String id, @RequestBody ExamDto request) {
        return ResponseEntity.ok(examService.updateExam(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> getExam(@PathVariable String id) {
        return ResponseEntity.ok(examService.getExam(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamDto>> getExams() {
        return ResponseEntity.ok(examService.getExams());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable String id) {
        return ResponseEntity.ok(examService.deleteExam(id));
    }
}
