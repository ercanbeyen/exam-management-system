package com.ercanbeyen.studentservice.service;

import com.ercanbeyen.studentservice.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto student);
    StudentDto updateStudent(String id, StudentDto student);
    StudentDto getStudent(String id);
    List<StudentDto> getStudents();
    String deleteStudent(String id);
}
