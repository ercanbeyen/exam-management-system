package com.ercanbeyen.servicecommon.client;

import com.ercanbeyen.servicecommon.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", configuration = FeignConfig.class)
public interface AuthServiceClient {
    @GetMapping("/auth/{username}/check-role")
    ResponseEntity<Boolean> checkUserRole(@PathVariable("username") String username, @RequestParam("role") String role);
}
