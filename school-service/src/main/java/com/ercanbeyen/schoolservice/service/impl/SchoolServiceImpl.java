package com.ercanbeyen.schoolservice.service.impl;

import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.mapper.SchoolMapper;
import com.ercanbeyen.schoolservice.repository.SchoolRepository;
import com.ercanbeyen.schoolservice.service.SchoolService;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    @Override
    public SchoolDto createSchool(SchoolDto request) {
        checkSchoolExists(request);
        School school = schoolMapper.dtoToEntity(request);
        return schoolMapper.entityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto updateSchool(String id, SchoolDto request) {
        School school = findById(id);

        if (!school.getName().equals(request.name())) {
            checkSchoolExists(request);
        }

        school.setName(request.name());
        school.setLocation(request.location());
        school.setOwner(request.owner());
        school.setClassrooms(request.classrooms());

        return schoolMapper.entityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto getSchool(String name) {
        return schoolMapper.entityToDto(findByName(name));
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
                .orElseThrow(() -> new ResourceNotFoundException("School is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "School", id);

        return school;
    }

    private School findByName(String name) {
        School school = schoolRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("School is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "School", name);

        return school;
    }

    private void checkSchoolExists(SchoolDto request) {
        String name = request.name();

        if (schoolRepository.existsByName(name)) {
            throw new ResourceConflictException("School already exists");
        }

        log.info("School {} has not existed before", name);
    }
}
