package com.ercanbeyen.examservice.dto;

public record ExamEventDto(String id, String examId, String candidateId, Integer schoolId, String classroomId) {

}
