package com.ercanbeyen.examservice.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamPeriodDto(
        @NotNull(message = "Date is mandatory")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate date,
        @NotNull(message = "Start time is mandatory")
        LocalTime startTime,
        @NotNull(message = "Finish time is mandatory")
        LocalTime finishTime) {

}
