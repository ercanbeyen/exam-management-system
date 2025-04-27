package com.ercanbeyen.examservice.mapper;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExamRegistrationMapper {
    @Mapping(source = "examEvent", target = "examEventDto", qualifiedByName = "convertExamEventToDto")
    ExamRegistrationDto entityToDto(ExamRegistration examRegistration);
    ExamRegistration dtoToEntity(ExamRegistrationDto examRegistrationDto);

    @Named("convertExamEventToDto")
    static ExamEventDto convertExamEventToDto(ExamEvent examEvent) {
        return new ExamEventDto(
                examEvent.getId(),
                examEvent.getExam().getSubject(),
                examEvent.getLocation(),
                examEvent.getProctors(),
                examEvent.getExamRegistrations()
                        .stream()
                        .map(ExamRegistration::getId)
                        .toList()
        );
    }
}
