package com.ercanbeyen.schoolservice.service.impl;

import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.repository.SchoolRepository;
import com.ercanbeyen.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;

    @Override
    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public School updateSchool(Integer id, School school) {
        School schoolInDb = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School is not found"));

        schoolInDb.setName(school.getName());
        schoolInDb.setLocation(school.getLocation());
        schoolInDb.setOwner(school.getOwner());


        return schoolRepository.save(schoolInDb);
    }

    @Override
    public School getSchool(Integer id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School is not found"));
    }

    @Override
    public List<School> getSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public String deleteSchool(Integer id) {
        schoolRepository.deleteById(id);
        return "School " + id + " is successfully deleted";
    }
}
