package com.ercanbeyen.examservice.dto;

import java.time.LocalDateTime;

public record ExamRegistrationDto(String id, String candidateId, ExamEventDto examEventDto, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
