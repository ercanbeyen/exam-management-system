package com.ercanbeyen.examservice.dto;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

public record ExamResultDto(
        String id,
        @NotBlank(message = "Exam registration id is mandatory")
        String examRegistrationId,
        @Range(min = 0, max = 100, message = "Score should be between {min} and {max}")
        Double score,
        LocalDateTime announcedAt) {

}
