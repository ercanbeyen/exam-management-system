package com.ercanbeyen.examservice.entity;

import com.ercanbeyen.examservice.embeddable.ExamTime;
import jakarta.persistence.*;
import lombok.Data;

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
}
