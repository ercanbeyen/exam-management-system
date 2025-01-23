package com.ercanbeyen.examservice.dto;

import java.time.LocalDateTime;

public record ExamRegistrationDto(String id, String examEventId, String candidateId, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
