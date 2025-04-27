package com.ercanbeyen.examservice.dto.response;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.embeddable.ExamPeriod;

public record ExamEntry(String candidateId, String examSubject, ExamLocation examLocation, ExamPeriod examPeriod) {

}
