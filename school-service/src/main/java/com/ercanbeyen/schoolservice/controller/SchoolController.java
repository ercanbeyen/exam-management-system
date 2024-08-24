package com.ercanbeyen.schoolservice.controller;

import com.ercanbeyen.schoolservice.entity.School;
import com.ercanbeyen.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        return ResponseEntity.ok(schoolService.createSchool(school));
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable int id, @RequestBody School school) {
        return ResponseEntity.ok(schoolService.updateSchool(id, school));
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchool(@PathVariable int id) {
        return ResponseEntity.ok(schoolService.getSchool(id));
    }

    @GetMapping
    public ResponseEntity<List<School>> getSchools() {
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable int id) {
        return ResponseEntity.ok(schoolService.deleteSchool(id));
    }
}
