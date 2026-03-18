package com.ercanbeyen.examservice.dto.builder;

import com.ercanbeyen.examservice.dto.response.ExamResultResponse;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.entity.ExamResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExamResultResponseBuilder {
    public ExamResultResponse build(ExamResult examResult) {
        ExamRegistration examRegistration = examResult.getExamRegistration();
        return new ExamResultResponse(
                examResult.getId(),
                examRegistration.getExamEvent()
                        .getExam()
                        .getSubject(),
                examRegistration.getCandidateId(),
                examResult.getScore(),
                examResult.getAnnouncedAt());
    }
}
