package com.ercanbeyen.schoolservice.service;

import com.ercanbeyen.schoolservice.entity.School;

import java.util.List;

public interface SchoolService {
    School createSchool(School school);
    School updateSchool(Integer id, School school);
    School getSchool(Integer id);
    List<School> getSchools();
    String deleteSchool(Integer id);
}
