package com.ercanbeyen.examservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamDto(String id, String subject, LocalDate date, LocalTime startedAt, LocalTime finishedAt) {

}
