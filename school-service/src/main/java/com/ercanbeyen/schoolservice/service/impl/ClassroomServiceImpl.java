package com.ercanbeyen.schoolservice.service.impl;

import com.ercanbeyen.schoolservice.dto.ClassroomDto;
import com.ercanbeyen.schoolservice.entity.Classroom;
import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.mapper.ClassroomMapper;
import com.ercanbeyen.schoolservice.repository.ClassroomRepository;
import com.ercanbeyen.schoolservice.service.ClassroomService;
import com.ercanbeyen.schoolservice.service.SchoolService;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    private final SchoolService schoolService;

    @Override
    public ClassroomDto createClassroom(ClassroomDto request) {
        Classroom classroom = classroomMapper.dtoToEntity(request);
        School school = schoolService.findById(request.schoolId());
        classroom.setSchool(school);
        return classroomMapper.entityToDto(classroomRepository.save(classroom));
    }

    @Override
    public ClassroomDto updateClassroom(String id, ClassroomDto request) {
        Classroom classroom = findById(id);
        School school = schoolService.findById(request.schoolId());

        classroom.setName(request.name());
        classroom.setSchool(school);

        return classroomMapper.entityToDto(classroomRepository.save(classroom));
    }

    @Override
    public ClassroomDto getClassroom(String id) {
        return classroomMapper.entityToDto(findById(id));
    }

    @Override
    public List<ClassroomDto> getClassrooms() {
        List<ClassroomDto> classroomDtos = new ArrayList<>();

        classroomRepository.findAll()
                .forEach(classroom -> classroomDtos.add(classroomMapper.entityToDto(classroom)));

        log.info("Classrooms are fetched");

        return classroomDtos;
    }

    @Override
    public String deleteClassroom(String id) {
        Classroom classroom = findById(id);
        classroomRepository.delete(classroom);
        return "Classroom " + id + " is successfully deleted";
    }

    private Classroom findById(String id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom is not found"));

        log.info("Classroom {} is found", id);

        return classroom;
    }
}
