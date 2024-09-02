package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.schoolservice.entity.Classroom;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    @Mapping(source = "classrooms", target = "classroomIds", qualifiedByName = "entityToId")
    SchoolDto entityToDto(School school);
    School dtoToEntity(SchoolDto schoolDto);

    @Named("entityToId")
    static String entityToId(Classroom classroom) {
        return classroom.getId();
    }
}
