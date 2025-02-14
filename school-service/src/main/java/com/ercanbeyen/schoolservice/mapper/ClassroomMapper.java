package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.schoolservice.entity.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomDto entityToDto(Classroom classroom);
    Classroom dtoToEntity(ClassroomDto classroomDto);
}
