package com.ercanbeyen.examservice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExamPeriod {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime finishTime;
}
