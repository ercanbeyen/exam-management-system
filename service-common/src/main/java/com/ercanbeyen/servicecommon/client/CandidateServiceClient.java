package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.config.FeignConfig;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "candidate-service", configuration = FeignConfig.class)
public interface CandidateServiceClient {
    @GetMapping("/candidates/{id}")
    ResponseEntity<CandidateDto> getCandidate(@PathVariable("id") String id);
}
