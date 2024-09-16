package com.ercanbeyen.examservice.dto;

import java.util.List;

public record ExamEventDto(String id, String examId, Integer schoolId, String classroomId, List<String> examRegistrationIds) {

}
