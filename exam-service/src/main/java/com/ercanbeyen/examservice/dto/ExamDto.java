package com.ercanbeyen.examservice.dto;

import java.util.List;

public record ExamDto(String id, String subject, RegistrationPeriodDto registrationPeriod, ExamPeriodDto examPeriod, List<String> examEventIds) {

}
