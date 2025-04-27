package com.ercanbeyen.schoolservice.service;

import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;

import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto request);
    SchoolDto updateSchool(String id, SchoolDto request);
    SchoolDto getSchool(String name);
    List<SchoolDto> getSchools();
    String deleteSchool(String id);
    School findById(String id);
}
