package com.ercanbeyen.examservice.client;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.servicecommon.client.exception.InternalServerErrorException;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchoolClient {
    private final SchoolServiceClient schoolServiceClient;

    public void checkClassroomCapacityForExamRegistration(ExamEvent examEvent) {
        ExamLocation examLocation = examEvent.getLocation();
        ResponseEntity<SchoolDto> schoolServiceResponse = schoolServiceClient.getSchool(examLocation.getSchool());

        String classroomName = examEvent.getLocation().getClassroom();

        assert schoolServiceResponse.getBody() != null;
        int classroomCapacity = schoolServiceResponse.getBody()
                .classrooms()
                .stream()
                .filter(classroomInSchool -> classroomInSchool.getName().equals(classroomName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Classroom " + classroomName + " is not found in school " + examEvent.getLocation().getSchool()))
                .getCapacity();

        int numberOfRegistrations = examEvent.getExamRegistrations().size();

        log.info("Classroom capacity and number of registrations: {} & {}", classroomCapacity, numberOfRegistrations);
        String subject = examEvent.getExam().getSubject();

        if (numberOfRegistrations > classroomCapacity) {
            log.error("Unexpected case! Number of registrations should not be greater than classroom capacity");
            throw new InternalServerErrorException("Internal server error occurred while registration to exam " + subject);
        }

        if (numberOfRegistrations == classroomCapacity) {
            throw new ResourceConflictException("Capacity is full for classroom " + classroomName);
        }

        log.info("Classroom {} is available for registration to exam {}", classroomName, subject);
    }
}
