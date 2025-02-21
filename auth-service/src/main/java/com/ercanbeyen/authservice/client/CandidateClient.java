package com.ercanbeyen.authservice.client;

import com.ercanbeyen.authservice.dto.request.RegistrationRequest;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class CandidateClient {
    private final RestTemplate restTemplate;

    public void createCandidate(RegistrationRequest request) throws URISyntaxException {
        URI uri = new URI("http://localhost:" + 8082 + "/candidates");
        restTemplate.postForObject(uri, request.candidateDto(), CandidateDto.class);
    }
}
