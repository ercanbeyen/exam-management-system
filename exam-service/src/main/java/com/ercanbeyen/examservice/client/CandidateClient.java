package com.ercanbeyen.examservice.client;

import com.ercanbeyen.servicecommon.client.CandidateServiceClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CandidateClient {
    private final CandidateServiceClient candidateServiceClient;

    public void checkCandidate(String loggedInUsername, String candidateId) {
        getCandidate(loggedInUsername, candidateId);
        log.info(LogMessage.RESOURCE_FOUND, "Candidate", candidateId);
    }


    public String getCandidateId(String loggedInUsername) {
        ResponseEntity<CandidateDto> candidateServiceResponse = candidateServiceClient.getCandidateByUsername(loggedInUsername, loggedInUsername);
        log.debug(LogMessage.CLIENT_SERVICE_RESPONSE, "Candidate", candidateServiceResponse);

        assert candidateServiceResponse.getBody() != null;
        return candidateServiceResponse.getBody()
                .id();
    }

    private void getCandidate(String loggedInUsername, String candidateId) {
        ResponseEntity<CandidateDto> candidateServiceResponse = candidateServiceClient.getCandidate(candidateId, loggedInUsername);
        log.debug(LogMessage.CLIENT_SERVICE_RESPONSE, "Candidate", candidateServiceResponse);
    }
}
