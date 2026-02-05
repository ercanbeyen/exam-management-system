package com.ercanbeyen.examservice.dto;


public record ExamResultDto(
        String id,
        String examRegistrationId,
        String candidateId,
        Double score) {

}
