package com.ercanbeyen.examservice.entity;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
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
    @Embedded
    private ExamLocation location;
    @OneToMany(mappedBy = "examEvent")
    private List<ExamRegistration> examRegistrations;
}
