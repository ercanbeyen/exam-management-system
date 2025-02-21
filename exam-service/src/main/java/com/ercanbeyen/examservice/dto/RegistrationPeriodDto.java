package com.ercanbeyen.examservice.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record RegistrationPeriodDto(
        @NotNull(message = "Begin at is mandatory")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime beginAt,
        @NotNull(message = "End at is mandatory")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime endAt) {

}
