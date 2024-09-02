package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.schoolservice.dto.ClassroomDto;
import com.ercanbeyen.schoolservice.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    @Mapping(target = "schoolId", source = "school.id")
    ClassroomDto entityToDto(Classroom classroom);
    Classroom dtoToEntity(ClassroomDto classroomDto);
}
