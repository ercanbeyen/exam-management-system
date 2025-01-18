package com.ercanbeyen.candidateservice.controller;

import com.ercanbeyen.candidateservice.util.AuthUtil;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.candidateservice.service.CandidateService;
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
    private final AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@RequestBody CandidateDto request) {
        return ResponseEntity.ok(candidateService.createCandidate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable String id, @RequestBody CandidateDto request, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, request, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        log.info("loggedInUsername: {}", username);
        return ResponseEntity.ok(candidateService.getCandidate(id, username));
    }

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getCandidates(@RequestHeader("loggedInUser") String username) {
        authUtil.checkUserHasAdminRole(username);
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable String id, @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.deleteCandidate(id, username));
    }
}
