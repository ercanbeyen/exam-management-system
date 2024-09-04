package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.contract.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("student-service")
public interface StudentServiceClient {
    @GetMapping("/students/{id}")
    ResponseEntity<StudentDto> getStudent(@PathVariable("id") String id);
}
