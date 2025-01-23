package com.ercanbeyen.schoolservice.controller;

import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.schoolservice.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody ClassroomDto request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDto> updateClassroom(@PathVariable("id") String id, @RequestBody ClassroomDto request) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable("id") String id) {
        return ResponseEntity.ok(classroomService.getClassroom(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDto>> getClassrooms() {
        return ResponseEntity.ok(classroomService.getClassrooms());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchoolClassroom(@PathVariable String id) {
        return ResponseEntity.ok(classroomService.deleteClassroom(id));
    }
}
