package com.ercanbeyen.examservice.dto;

import com.ercanbeyen.examservice.embeddable.ExamPeriod;
import com.ercanbeyen.examservice.embeddable.RegistrationPeriod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExamDto(
        String id,
        @NotBlank(message = "Subject is mandatory")
        String subject,
        @Valid
        RegistrationPeriod registrationPeriod,
        @Valid
        ExamPeriod period,
        List<String> examEventIds) {

}
