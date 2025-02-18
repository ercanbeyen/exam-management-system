package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExamEventMapper {
    @Mapping(target = "examSubject", source = "exam.subject")
    @Mapping(source = "examRegistrations", target = "examRegistrationIds", qualifiedByName = "entityToId")
    ExamEventDto entityToDto(ExamEvent examEvent);
    ExamEvent dtoToEntity(ExamEventDto examEventDto);

    @Named("entityToId")
    static String entityToId(ExamRegistration examRegistration) {
        return examRegistration.getId();
    }
}
