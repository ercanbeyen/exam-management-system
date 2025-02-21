package com.ercanbeyen.servicecommon.client.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CandidateDto(
        String id,
        @NotBlank(message = "Username is mandatory")
        String username,
        @NotBlank(message = "Full name is mandatory")
        String fullName,
        @NotNull(message = "Age is mandatory")
        int age,
        @NotBlank(message = "Gender is mandatory")
        String gender,
        @NotBlank(message = "School name is mandatory")
        String schoolName) {

}
