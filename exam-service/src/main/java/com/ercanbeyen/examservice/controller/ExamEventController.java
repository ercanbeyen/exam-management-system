package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exam-events")
public class ExamEventController {
    private final ExamEventService examEventService;

    @PostMapping
    public ResponseEntity<ExamEventDto> createExamEvent(@RequestBody ExamEventDto request) {
        return ResponseEntity.ok(examEventService.createExamEvent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamEventDto> updateExamEvent(@PathVariable String id, @RequestBody ExamEventDto request) {
        return ResponseEntity.ok(examEventService.updateExamEvent(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamEventDto> getExamEvent(@PathVariable String id) {
        return ResponseEntity.ok(examEventService.getExamEvent(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamEventDto>> getExamEvents() {
        return ResponseEntity.ok(examEventService.getExamEvents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamEvent(@PathVariable String id) {
        return ResponseEntity.ok(examEventService.deleteExamEvent(id));
    }
}
