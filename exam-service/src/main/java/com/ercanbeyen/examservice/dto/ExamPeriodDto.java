package com.ercanbeyen.examservice.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamPeriodDto(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date, LocalTime startTime, LocalTime finishTime) {

}
