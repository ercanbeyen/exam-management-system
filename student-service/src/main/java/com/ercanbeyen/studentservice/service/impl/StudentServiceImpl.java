package com.ercanbeyen.studentservice.service.impl;

import com.ercanbeyen.studentservice.dto.StudentDto;
import com.ercanbeyen.studentservice.entity.Student;
import com.ercanbeyen.studentservice.mapper.StudentMapper;
import com.ercanbeyen.studentservice.repository.StudentRepository;
import com.ercanbeyen.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = studentMapper.dtoToEntity(studentDto);
        return studentMapper.entityToDto(studentRepository.save(student));
    }

    @Override
    public StudentDto updateStudent(String id, StudentDto request) {
        Student studentInDb = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        studentInDb.setFullName(request.fullName());
        studentInDb.setAge(request.age());
        studentInDb.setGender(request.gender());

        return studentMapper.entityToDto(studentRepository.save(studentInDb));
    }

    @Override
    public StudentDto getStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        return studentMapper.entityToDto(student);
    }

    @Override
    public List<StudentDto> getStudents() {
        List<StudentDto> studentDtos = new ArrayList<>();

        studentRepository.findAll()
                .forEach(student -> studentDtos.add(studentMapper.entityToDto(student)));

        return studentDtos;
    }

    @Override
    public String deleteStudent(String id) {
        studentRepository.deleteById(id);
        return "Student " + id + " is successfully deleted";
    }
}
