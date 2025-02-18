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
    @Column(unique = true, nullable = false)
    private String subject;
    @Embedded
    private RegistrationPeriod registrationPeriod;
    @Embedded
    private ExamPeriod examPeriod;
    @OneToMany(mappedBy = "exam")
    private List<ExamEvent> examEvents;

    @Override
    public String toString() {
        List<String> examEventsIds = examEvents.stream()
                .map(ExamEvent::getId)
                .toList();

        return "Exam{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", registrationPeriod=" + registrationPeriod +
                ", examPeriod=" + examPeriod +
                ", examEvents=" + examEventsIds +
                '}';
    }
}
