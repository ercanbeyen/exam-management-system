package com.ercanbeyen.examservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ExamLocationDto(
        @NotBlank(message = "School name is mandatory")
        String schoolName,
        @NotBlank(message = "Classroom name is mandatory")
        String classroomName) {

}
