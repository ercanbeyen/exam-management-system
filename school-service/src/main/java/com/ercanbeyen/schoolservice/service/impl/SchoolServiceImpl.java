package com.ercanbeyen.schoolservice.service.impl;

import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.mapper.SchoolMapper;
import com.ercanbeyen.schoolservice.repository.SchoolRepository;
import com.ercanbeyen.schoolservice.service.SchoolService;
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

    @Override
    public SchoolDto createSchool(SchoolDto request) {
        School school = schoolMapper.dtoToEntity(request);
        return schoolMapper.entityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto updateSchool(Integer id, SchoolDto request) {
        School schoolInDb = findById(id);

        schoolInDb.setName(request.name());
        schoolInDb.setLocation(request.location());
        schoolInDb.setOwner(request.owner());


        return schoolMapper.entityToDto(schoolRepository.save(schoolInDb));
    }

    @Override
    public SchoolDto getSchool(Integer id) {
        School school = findById(id);
        return schoolMapper.entityToDto(school);
    }

    @Override
    public List<SchoolDto> getSchools() {
        List<SchoolDto> schoolDtos = new ArrayList<>();

        schoolRepository.findAll()
                .forEach(school -> schoolDtos.add(schoolMapper.entityToDto(school)));

        log.info("Schools are fetched");

        return schoolDtos;
    }

    @Override
    public String deleteSchool(Integer id) {
        schoolRepository.deleteById(id);
        return "School " + id + " is successfully deleted";
    }

    private School findById(Integer id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School is not found"));

        log.info("School is found");

        return school;
    }
}
