package com.ercanbeyen.examservice.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record RegistrationPeriodDto(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginAt, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt) {

}
