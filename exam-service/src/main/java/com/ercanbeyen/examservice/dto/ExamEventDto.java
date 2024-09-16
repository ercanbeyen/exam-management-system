package com.ercanbeyen.examservice.dto;

import java.util.List;

public record ExamEventDto(String id, String examId,ExamLocationDto location, List<String> examRegistrationIds) {

}
