package com.ercanbeyen.schoolservice.controller;

import com.ercanbeyen.schoolservice.validator.SchoolValidator;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.service.SchoolService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<SchoolDto> createSchool(@RequestBody @Valid SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.createSchool(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable String id, @RequestBody @Valid SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.updateSchool(id, request));
    }

    @GetMapping("/{name}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable String name) {
        SchoolDto school = schoolService.getSchool(name);
        log.info("School is successfully fetched");
        return ResponseEntity.ok(school);
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
