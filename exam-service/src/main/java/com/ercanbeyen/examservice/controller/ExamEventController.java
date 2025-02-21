package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.client.AuthClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exam-events")
public class ExamEventController {
    private final ExamEventService examEventService;
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<ExamEventDto> createExamEvent(@RequestBody @Valid ExamEventDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.createExamEvent(request, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamEventDto> updateExamEvent(@PathVariable String id, @RequestBody @Valid ExamEventDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
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
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.deleteExamEvent(id, username));
    }
}
