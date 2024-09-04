package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exam_events")
public class ExamEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Exam exam;
    private String studentId;
    private Integer schoolId;
    private String classroomId;
}
