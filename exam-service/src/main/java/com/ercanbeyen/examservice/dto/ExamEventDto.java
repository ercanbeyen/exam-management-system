package com.ercanbeyen.examservice.dto;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public record ExamEventDto(
        String id,
        @NotBlank(message = "Exam subject is mandatory")
        String examSubject,
        @Valid
        ExamLocation location,
        @NotEmpty(message = "There should be at least 1 proctor")
        Set<@NotBlank(message = "Invalid proctor") String> proctors,
        List<String> examRegistrationIds) {

}
