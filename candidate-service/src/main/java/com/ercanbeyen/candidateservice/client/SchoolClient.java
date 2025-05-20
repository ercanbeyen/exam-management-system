package com.ercanbeyen.candidateservice.client;

import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchoolClient {
    private final SchoolServiceClient schoolServiceClient;

    public void checkSchool(CandidateDto request) {
        ResponseEntity<SchoolDto> schoolServiceResponse = schoolServiceClient.getSchool(request.schoolName());
        log.debug(LogMessage.CLIENT_SERVICE_RESPONSE, "School", schoolServiceResponse);
        log.info(LogMessage.RESOURCE_FOUND, "School", request.schoolName());
    }
}
