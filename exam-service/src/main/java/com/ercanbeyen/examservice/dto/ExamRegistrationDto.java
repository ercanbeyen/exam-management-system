package com.ercanbeyen.examservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ExamRegistrationDto(
        String id,
        @NotBlank(message = "Candidate id is mandatory")
        String candidateId,
        @Valid
        ExamEventDto examEventDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
