package com.ercanbeyen.studentservice.service.impl;

import com.ercanbeyen.studentservice.entity.Student;
import com.ercanbeyen.studentservice.repository.StudentRepository;
import com.ercanbeyen.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(String id, Student student) {
        Student studentInDb = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        studentInDb.setFullName(student.getFullName());
        studentInDb.setAge(student.getAge());
        studentInDb.setGender(student.getGender());

        return studentRepository.save(studentInDb);
    }

    @Override
    public Student getStudent(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student is not found"));
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public String deleteStudent(String id) {
        studentRepository.deleteById(id);
        return "Student " + id + " is successfully deleted";
    }
}
