package com.ercanbeyen.schoolservice.service;

import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;

import java.util.List;

public interface ClassroomService {
    ClassroomDto createClassroom(ClassroomDto request);
    ClassroomDto updateClassroom(String id, ClassroomDto request);
    ClassroomDto getClassroom(String id);
    List<ClassroomDto> getClassrooms();
    String deleteClassroom(String id);
}
