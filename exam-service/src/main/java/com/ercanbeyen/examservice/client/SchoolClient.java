package com.ercanbeyen.examservice.client;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.servicecommon.client.exception.InternalServerErrorException;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
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
        String classroomName = examLocation.getClassroomName();
        ResponseEntity<ClassroomDto> schoolServiceResponse = schoolServiceClient.getClassroom(examLocation.getSchoolName(), classroomName);

        assert schoolServiceResponse.getBody() != null;
        ClassroomDto classroomDto = schoolServiceResponse.getBody();

        int classroomCapacity = classroomDto.capacity();
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
