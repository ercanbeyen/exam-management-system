package com.ercanbeyen.schoolservice.mapper;

import com.ercanbeyen.schoolservice.embeddable.Classroom;
import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    @Mapping(source = "classrooms", target = "classroomDtos", qualifiedByName = "classroomEntityToDtoConverter")
    SchoolDto entityToDto(School school);
    @Mapping(source = "classroomDtos", target = "classrooms", qualifiedByName = "classroomDtoToEntityConverter")
    School dtoToEntity(SchoolDto schoolDto);

    @Named("classroomEntityToDtoConverter")
    static ClassroomDto dtoToEntity(Classroom classroom) {
        return new ClassroomDto(classroom.getName(), classroom.getCapacity());
    }

    @Named("classroomDtoToEntityConverter")
    static Classroom entityToDto(ClassroomDto classroomDto) {
        return new Classroom(classroomDto.name(), classroomDto.capacity());
    }
}
