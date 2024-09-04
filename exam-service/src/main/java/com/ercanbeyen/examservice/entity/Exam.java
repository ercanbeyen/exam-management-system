package com.ercanbeyen.examservice.entity;

import com.ercanbeyen.examservice.embeddable.ExamTime;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String subject;
    @Embedded
    private ExamTime time;
    @OneToMany(mappedBy = "exam")
    private List<ExamEvent> examEvents;
}
