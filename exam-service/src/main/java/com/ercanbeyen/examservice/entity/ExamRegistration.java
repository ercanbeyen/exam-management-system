package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ExamRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private ExamEvent examEvent;
    private String candidateId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "ExamRegistration{" +
                "id='" + id + '\'' +
                ", examEvent=" + examEvent.getId() +
                ", candidateId='" + candidateId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
