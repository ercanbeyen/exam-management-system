package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.contract.StudentDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("student-service")
public interface StudentServiceClient {
    @CircuitBreaker(name = "STUDENT-SERVICE", fallbackMethod = "fallbackMethodOfGetStudent")
    @GetMapping("/students/{id}")
    ResponseEntity<StudentDto> getStudent(@PathVariable("id") String id);

    default ResponseEntity<StudentDto> fallbackMethodOfGetStudent(Exception exception) {
        return ResponseEntity.ok(new StudentDto(null, null, 0, null, null));
    }
}
