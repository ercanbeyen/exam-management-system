package com.ercanbeyen.examservice.dto.response;

import java.time.LocalDateTime;

public record ExamResultResponse(String id, String subject, String candidateId, Double score, LocalDateTime announcedAt) {

}
