package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("candidate-service")
public interface CandidateServiceClient {
    @CircuitBreaker(name = "CANDIDATE-SERVICE", fallbackMethod = "fallbackMethodOfGetCandidate")
    @GetMapping("/candidates/{id}")
    ResponseEntity<CandidateDto> getCandidate(@PathVariable("id") String id);

    default ResponseEntity<CandidateDto> fallbackMethodOfGetCandidate(Exception exception) {
        return ResponseEntity.ok(new CandidateDto(null, null, 0, null, null));
    }
}
