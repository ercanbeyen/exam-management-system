package com.ercanbeyen.schoolservice.controller;

import com.ercanbeyen.schoolservice.validator.SchoolValidator;
import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<SchoolDto> createSchool(@RequestBody SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.createSchool(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable String id, @RequestBody SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.updateSchool(id, request));
    }

    @GetMapping("/{name}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable String name) {
        SchoolDto school = schoolService.getSchool(name);
        log.info("School is successfully fetched");
        return ResponseEntity.ok(school);
    }

    @GetMapping("/{name}/classrooms/{classroom}")
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable String name, @PathVariable("classroom") String classroomName) {
        ClassroomDto classroom = schoolService.getClassroom(name, classroomName);
        log.info("Classroom is successfully fetched");
        return ResponseEntity.ok(classroom);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDto>> getSchools() {
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable String id) {
        return ResponseEntity.ok(schoolService.deleteSchool(id));
    }
}
