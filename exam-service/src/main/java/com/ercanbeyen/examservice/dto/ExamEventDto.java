package com.ercanbeyen.examservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExamEventDto(
        String id,
        @NotBlank(message = "Exam subject is mandatory")
        String examSubject,
        @Valid
        ExamLocationDto location,
        List<String> examRegistrationIds) {

}
