package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.schoolservice.dto.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolDto entityToDto(School school);
    School dtoToEntity(SchoolDto schoolDto);
}
