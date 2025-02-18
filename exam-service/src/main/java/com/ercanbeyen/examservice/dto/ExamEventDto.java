package com.ercanbeyen.examservice.dto;

import java.util.List;

public record ExamEventDto(String id, String examSubject, ExamLocationDto location, List<String> examRegistrationIds) {

}
