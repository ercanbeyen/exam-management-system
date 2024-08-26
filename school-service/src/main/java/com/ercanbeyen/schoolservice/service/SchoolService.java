package com.ercanbeyen.schoolservice.service;

import com.ercanbeyen.servicecommon.client.contract.SchoolDto;

import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto request);
    SchoolDto updateSchool(Integer id, SchoolDto request);
    SchoolDto getSchool(Integer id);
    List<SchoolDto> getSchools();
    String deleteSchool(Integer id);
}
