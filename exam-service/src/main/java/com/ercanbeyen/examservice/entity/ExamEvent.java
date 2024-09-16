package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "exam_events")
public class ExamEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Exam exam;
    private String candidateId;
    private Integer schoolId;
    private String classroomId;
    @OneToMany(mappedBy = "examEvent")
    private List<ExamRegistration> examRegistrations;
}
