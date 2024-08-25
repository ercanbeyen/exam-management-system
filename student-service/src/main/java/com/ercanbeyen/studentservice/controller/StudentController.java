package com.ercanbeyen.studentservice.controller;

import com.ercanbeyen.studentservice.dto.StudentDto;
import com.ercanbeyen.studentservice.entity.Student;
import com.ercanbeyen.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto request) {
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String id, @RequestBody StudentDto request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }
}
