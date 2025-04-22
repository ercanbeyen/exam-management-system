package com.ercanbeyen.examservice.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExamPeriod {
    @NotNull(message = "Date is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDate date;
    @NotNull(message = "Start time is mandatory")
    LocalTime startTime;
    @NotNull(message = "Finish time is mandatory")
    LocalTime finishTime;
}
