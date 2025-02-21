package com.ercanbeyen.examservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExamDto(
        String id,
        @NotBlank(message = "Subject is mandatory")
        String subject,
        @Valid
        RegistrationPeriodDto registrationPeriod,
        @Valid
        ExamPeriodDto examPeriod,
        List<String> examEventIds) {

}
