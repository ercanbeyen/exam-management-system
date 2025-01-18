package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exam-events")
public class ExamEventController {
    private final ExamEventService examEventService;
    private final AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<ExamEventDto> createExamEvent(@RequestBody ExamEventDto request, @RequestHeader("loggedInUser") String username) {
        authUtil.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.createExamEvent(request, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamEventDto> updateExamEvent(@PathVariable String id, @RequestBody ExamEventDto request, @RequestHeader("loggedInUser") String username) {
        authUtil.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.updateExamEvent(id, request, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamEventDto> getExamEvent(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examEventService.getExamEvent(id, username));
    }

    @GetMapping
    public ResponseEntity<List<ExamEventDto>> getExamEvents(@RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examEventService.getExamEvents(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamEvent(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        authUtil.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.deleteExamEvent(id, username));
    }
}
