package com.ercanbeyen.examservice.entity;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "exam_events")
public class ExamEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "exam_subject", referencedColumnName = "subject")
    private Exam exam;
    @Embedded
    private ExamLocation location;
    private Set<String> proctors;
    @OneToMany(mappedBy = "examEvent")
    private List<ExamRegistration> examRegistrations;

    @Override
    public String toString() {
        List<String> examRegistrationIds = examRegistrations.stream()
                .map(ExamRegistration::getId)
                .toList();

        return "ExamEvent{" +
                "id='" + id + '\'' +
                ", exam=" + exam +
                ", location=" + location +
                ", examRegistrations=" + examRegistrationIds +
                '}';
    }
}
