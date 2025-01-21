package com.ercanbeyen.examservice.entity;

import com.ercanbeyen.examservice.embeddable.ExamPeriod;
import com.ercanbeyen.examservice.embeddable.RegistrationPeriod;
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
    private RegistrationPeriod registrationPeriod;
    @Embedded
    private ExamPeriod examPeriod;
    @OneToMany(mappedBy = "exam")
    private List<ExamEvent> examEvents;
}
