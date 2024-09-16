package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExamRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private ExamEvent examEvent;
    private String candidateId;
}
