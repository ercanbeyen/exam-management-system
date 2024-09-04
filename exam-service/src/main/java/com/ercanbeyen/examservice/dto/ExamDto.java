package com.ercanbeyen.examservice.dto;

import java.util.List;

public record ExamDto(String id, String subject, ExamTimeDto time, List<String> examEventIds) {

}
