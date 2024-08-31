package com.ercanbeyen.examservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String subject;
    private LocalDate date;
    private LocalTime startedAt;
    private LocalTime finishedAt;
}
