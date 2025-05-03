package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.examservice.client.AuthClient;
import com.ercanbeyen.examservice.validator.ExamValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamController {
    private final ExamService examService;
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<ExamDto> createExam(@RequestBody @Valid ExamDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        ExamValidator.checkExamTime(request);
        return ResponseEntity.ok(examService.createExam(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDto> updateExam(@PathVariable String id, @RequestBody @Valid ExamDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        ExamValidator.checkExamTime(request);
        return ResponseEntity.ok(examService.updateExam(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> getExam(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.getExam(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamDto>> getExams(@RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.getExams());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.deleteExam(id));
    }
}
