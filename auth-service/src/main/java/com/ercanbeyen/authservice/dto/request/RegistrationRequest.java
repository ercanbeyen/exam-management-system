package com.ercanbeyen.authservice.dto.request;

import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
        @Valid
        CandidateDto candidateDto,
        @NotBlank(message = "Password is mandatory")
        String password) {

}
