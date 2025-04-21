package com.ercanbeyen.examservice.dto.response;

import com.ercanbeyen.examservice.dto.ExamLocationDto;
import com.ercanbeyen.examservice.dto.ExamPeriodDto;

public record ExamEntry(String candidateId, String examSubject, ExamLocationDto examLocationDto, ExamPeriodDto examPeriodDto) {

}
