package com.ercanbeyen.examservice.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExamLocation {
    @NotBlank(message = "School is mandatory")
    private String school;
    @NotBlank(message = "Classroom is mandatory")
    private String classroom;
}
