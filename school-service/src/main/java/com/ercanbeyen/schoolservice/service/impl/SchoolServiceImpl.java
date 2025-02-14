package com.ercanbeyen.schoolservice.service.impl;

import com.ercanbeyen.schoolservice.entity.Classroom;
import com.ercanbeyen.schoolservice.mapper.ClassroomMapper;
import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.mapper.SchoolMapper;
import com.ercanbeyen.schoolservice.repository.SchoolRepository;
import com.ercanbeyen.schoolservice.service.SchoolService;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;
    private final ClassroomMapper classroomMapper;

    @Override
    public SchoolDto createSchool(SchoolDto request) {
        School school = schoolMapper.dtoToEntity(request);
        return schoolMapper.entityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto updateSchool(String id, SchoolDto request) {
        School school = findById(id);
        school.setName(request.name());
        school.setLocation(request.location());
        school.setOwner(request.owner());
        List<Classroom> classrooms = new ArrayList<>();

        request.classroomDtos()
                        .forEach(classroomDto -> classrooms.add(classroomMapper.dtoToEntity(classroomDto)));

        school.setClassrooms(classrooms);

        return schoolMapper.entityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto getSchool(String id) {
        return schoolMapper.entityToDto(findById(id));
    }

    @Override
    public ClassroomDto getClassroom(String id, String classroomName) {
        School school = findById(id);

        Classroom classroom = school.getClassrooms()
                .stream()
                .filter(classroomInSchool -> classroomInSchool.getName().equals(classroomName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Classroom " + classroomName + " is not found in school " + school.getName()));

        return classroomMapper.entityToDto(classroom);
    }

    @Override
    public List<SchoolDto> getSchools() {
        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteSchool(String id) {
        schoolRepository.deleteById(id);
        return String.format("School %s is successfully deleted", id);
    }

    @Override
    public School findById(String id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("School %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "School", id);

        return school;
    }
}
