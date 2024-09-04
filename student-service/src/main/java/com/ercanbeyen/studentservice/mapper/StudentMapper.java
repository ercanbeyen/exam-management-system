package com.ercanbeyen.studentservice.mapper;

import com.ercanbeyen.servicecommon.client.contract.StudentDto;
import com.ercanbeyen.studentservice.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto entityToDto(Student student);
    Student dtoToEntity(StudentDto studentDto);
}
