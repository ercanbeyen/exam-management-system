package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.client.AuthClient;
import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.service.ExamResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-results")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamResultController {
    private final ExamResultService examResultService;
    private final AuthClient authClient;
    private final CandidateClient candidateClient;

    @PostMapping
    public ResponseEntity<ExamResultDto> createExamResult(@RequestBody @Valid ExamResultDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.createExamResult(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResultDto> updateExamResult(@PathVariable("id") String id, @RequestBody @Valid ExamResultDto request, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.updateExamResult(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResultDto> getExamResult(@PathVariable("id") String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examResultService.getExamResult(id, username));
    }

    @GetMapping
    public ResponseEntity<List<ExamResultDto>> getExamResults(@RequestParam("subject") String subject, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.getExamResults(subject));
    }

    @GetMapping("/candidates/{candidateId}")
    public ResponseEntity<Page<ExamResultDto>> getExamResultsOfCandidate(
            @PathVariable("candidateId") String candidateId,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "5") Integer pageSize,
            @RequestHeader("loggedInUser") String username) {
        candidateClient.checkCandidate(candidateId, username);
        return ResponseEntity.ok(examResultService.getExamResultsOfCandidate(candidateId, pageNumber, pageSize));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamResult(@PathVariable("id") String id, @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.deleteExamResult(id));
    }
}
