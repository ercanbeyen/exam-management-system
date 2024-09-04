package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.entity.ExamEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(source = "examEvents", target = "examEventIds", qualifiedByName = "entityToId")
    ExamDto entityToDto(Exam exam);
    Exam dtoToEntity(ExamDto examDto);

    @Named("entityToId")
    static String entityToId(ExamEvent examEvent) {
        return examEvent.getId();
    }
}
