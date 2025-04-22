package com.ercanbeyen.examservice.validator;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.embeddable.ExamPeriod;
import com.ercanbeyen.examservice.embeddable.RegistrationPeriod;
import com.ercanbeyen.servicecommon.client.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@UtilityClass
@Slf4j
public class ExamValidator {
    public void checkExamTime(ExamDto request) {
        checkRegistrationPeriod(request);
        checkExamPeriod(request);
    }

    private void checkExamPeriod(ExamDto request) {
        ExamPeriod examPeriod = request.period();
        RegistrationPeriod registrationPeriod = request.registrationPeriod();

        if (!examPeriod.getStartTime().isBefore(examPeriod.getFinishTime())) {
            throw new BadRequestException("Exam start time must be before finish time");
        }

        if (!examPeriod.getDate().isAfter(registrationPeriod.getEndAt().toLocalDate())) {
            throw new BadRequestException("Exam date must be after than registration end date");
        }

        log.info("Exam period is valid");
    }

    private void checkRegistrationPeriod(ExamDto request) {
        RegistrationPeriod registrationPeriod = request.registrationPeriod();

        if (!registrationPeriod.getBeginAt().isBefore(registrationPeriod.getEndAt())) {
            throw new BadRequestException("Registration begin date must be before end time");
        }

        if (!registrationPeriod.getEndAt().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Registration end date must after than now");
        }

        log.info("Registration period is valid");
    }
}
