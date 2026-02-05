package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {
    @Mapping(target = "examRegistrationId", source = "examRegistration.id")
    ExamResultDto entityToDto(ExamResult examResult);
    ExamResult dtoToEntity(ExamResultDto examResultDto);
}
