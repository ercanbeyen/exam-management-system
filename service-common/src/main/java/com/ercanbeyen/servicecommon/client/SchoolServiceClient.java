package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.config.FeignConfig;
import com.ercanbeyen.servicecommon.client.contract.ClassroomDto;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "school-service", configuration = FeignConfig.class)
public interface SchoolServiceClient {
    @GetMapping("/schools/{id}")
    ResponseEntity<SchoolDto> getSchool(@PathVariable("id") String id);
    @GetMapping("/schools/{id}/classroomDtos/{classroom}")
    ResponseEntity<ClassroomDto> getClassroom(@PathVariable("id") String id, @PathVariable("classroom") String classroomName);
}
