package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    private ExamRegistration examRegistration;
    private String candidateId;
    private Double score;
    private LocalDateTime announcedAt;
}
