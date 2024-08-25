package com.ercanbeyen.studentservice.service;

import com.ercanbeyen.studentservice.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student updateStudent(String id, Student student);
    Student getStudent(String id);
    List<Student> getStudents();
    String deleteStudent(String id);
}
