package com.ercanbeyen.examservice.validator;

import com.ercanbeyen.examservice.embeddable.RegistrationPeriod;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.exception.TimeExpiredException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@UtilityClass
public class ExamRegistrationValidator {
    public void checkExamRegistrationPeriod(Exam exam) {
        RegistrationPeriod registrationPeriod = exam.getRegistrationPeriod();
        LocalDateTime now = LocalDateTime.now();

        if (registrationPeriod.getBeginAt().isAfter(now)) {
            throw new TimeExpiredException("Registration period for exam " + exam.getSubject() + " has not been started yet");
        }

        if (registrationPeriod.getEndAt().isBefore(now)) {
            throw new TimeExpiredException("Registration period for exam " + exam.getSubject() + " has already been ended");
        }

        log.info("Current time is in registration period for the exam");
    }
}
