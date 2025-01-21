package com.ercanbeyen.examservice.util;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.dto.ExamPeriodDto;
import com.ercanbeyen.examservice.dto.RegistrationPeriodDto;
import com.ercanbeyen.servicecommon.client.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@UtilityClass
@Slf4j
public class TimeUtil {
    public void checkExamTime(ExamDto request) {
        checkRegistrationPeriod(request);
        checkExamPeriod(request);
    }

    private void checkExamPeriod(ExamDto request) {
        ExamPeriodDto examPeriod = request.examPeriod();
        RegistrationPeriodDto registrationPeriod = request.registrationPeriod();

        if (!examPeriod.startTime().isBefore(examPeriod.finishTime())) {
            throw new BadRequestException("Exam start time must be before finish time");
        }

        if (!examPeriod.date().isAfter(registrationPeriod.endAt().toLocalDate())) {
            throw new BadRequestException("Exam date must be after than registration end date");
        }

        log.info("Exam period is valid");
    }

    private void checkRegistrationPeriod(ExamDto request) {
        RegistrationPeriodDto registrationPeriod = request.registrationPeriod();

        if (!registrationPeriod.beginAt().isBefore(registrationPeriod.endAt())) {
            throw new BadRequestException("Registration begin date must be before end time");
        }

        if (!registrationPeriod.beginAt().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Registration begin date must after than now");
        }

        log.info("Registration period is valid");
    }
}
