package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamRegistrationMapper {
    @Mapping(target = "examEventId", source = "examEvent.id")
    ExamRegistrationDto entityToDto(ExamRegistration examRegistration);
    ExamRegistration dtoToEntity(ExamRegistrationDto examRegistrationDto);
}
