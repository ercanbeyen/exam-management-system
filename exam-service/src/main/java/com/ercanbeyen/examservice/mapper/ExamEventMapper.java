package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamEventMapper {
    @Mapping(target = "examId", source = "exam.id")
    ExamEventDto entityToDto(ExamEvent examEvent);
    ExamEvent dtoToEntity(ExamEventDto examEventDto);
}
