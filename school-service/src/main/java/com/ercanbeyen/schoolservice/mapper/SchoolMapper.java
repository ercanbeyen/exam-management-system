package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolDto entityToDto(School school);
    School dtoToEntity(SchoolDto schoolDto);
}
