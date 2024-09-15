package com.ercanbeyen.candidateservice.controller;

import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.candidateservice.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@RequestBody CandidateDto request) {
        return ResponseEntity.ok(candidateService.createCandidate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable String id, @RequestBody CandidateDto request) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable String id) {
        return ResponseEntity.ok(candidateService.getCandidates(id));
    }

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getCandidates() {
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable String id) {
        return ResponseEntity.ok(candidateService.deleteCandidate(id));
    }
}
