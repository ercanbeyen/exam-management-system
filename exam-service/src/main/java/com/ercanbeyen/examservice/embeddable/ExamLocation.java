package com.ercanbeyen.examservice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExamLocation {
    private Integer schoolId;
    private String classroomId;
}
