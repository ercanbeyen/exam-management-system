package com.ercanbeyen.candidateservice.controller;

import com.ercanbeyen.candidateservice.client.AuthClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.candidateservice.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
@Slf4j
public class CandidateController {
    private final CandidateService candidateService;
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@RequestBody @Valid CandidateDto request) {
        return ResponseEntity.ok(candidateService.createCandidate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable String id, @RequestBody @Valid CandidateDto request, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, request, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        log.info("loggedInUsername: {}", username);
        return ResponseEntity.ok(candidateService.getCandidate(id, username));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<CandidateDto> getCandidateByUsername(@PathVariable("username") String username, @RequestHeader("loggedInUser") String loggedInUsername) {
        log.info("loggedInUsername: {}", loggedInUsername);
        return ResponseEntity.ok(candidateService.getCandidateByUsername(username, loggedInUsername));
    }

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getCandidates(@RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.deleteCandidate(id, username));
    }
}
