package com.ercanbeyen.examservice.client;

import com.ercanbeyen.servicecommon.client.CandidateServiceClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CandidateClient {
    public static final String CANDIDATE = "Candidate";
    private final CandidateServiceClient candidateServiceClient;

    public void checkCandidate(String candidateId, String loggedInUsername) {
        getCandidate(candidateId, loggedInUsername);
        log.info(LogMessage.RESOURCE_FOUND, CANDIDATE, candidateId);
    }

    public void checkCandidateByUsername(String username) {
        candidateServiceClient.getCandidateByUsername(username, username);
        log.info(LogMessage.RESOURCE_FOUND, CANDIDATE, username);
    }

    public String getCandidateIdByUsername(String loggedInUsername) {
        ResponseEntity<CandidateDto> candidateServiceResponse = candidateServiceClient.getCandidateByUsername(loggedInUsername, loggedInUsername);
        log.debug(LogMessage.CLIENT_SERVICE_RESPONSE, CANDIDATE, candidateServiceResponse);

        assert candidateServiceResponse.getBody() != null;
        return candidateServiceResponse.getBody().id();
    }

    private void getCandidate(String candidateId, String loggedInUsername) {
        ResponseEntity<CandidateDto> candidateServiceResponse = candidateServiceClient.getCandidate(candidateId, loggedInUsername);
        log.debug(LogMessage.CLIENT_SERVICE_RESPONSE, CANDIDATE, candidateServiceResponse);
    }
}
