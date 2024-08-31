package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.entity.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    ExamDto entityToDto(Exam exam);
    Exam dtoToEntity(ExamDto examDto);
}
