package com.ercanbeyen.schoolservice.controller;

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
        return ResponseEntity.ok(schoolService.createSchool(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable int id, @RequestBody SchoolDto request) {
        return ResponseEntity.ok(schoolService.updateSchool(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable int id) {
        SchoolDto school = schoolService.getSchool(id);
        log.info("School is successfully fetched");
        return ResponseEntity.ok(school);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDto>> getSchools() {
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable int id) {
        return ResponseEntity.ok(schoolService.deleteSchool(id));
    }
}
