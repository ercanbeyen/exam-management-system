package com.ercanbeyen.examservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamTimeDto(LocalDate date, LocalTime startedAt, LocalTime finishedAt) {

}
