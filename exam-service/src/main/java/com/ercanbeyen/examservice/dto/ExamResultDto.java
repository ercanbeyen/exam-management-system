package com.ercanbeyen.examservice.dto;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record ExamResultDto(
        String id,
        @NotBlank(message = "Exam registration id is mandatory")
        String examRegistrationId,
        @NotBlank(message = "Candidate id is mandatory")
        String candidateId,
        @Range(min = 0, max = 100, message = "Score should be between {min} and {max}")
        Double score) {

}
