package com.ercanbeyen.authservice.controller;

import com.ercanbeyen.authservice.dto.request.LoginRequest;
import com.ercanbeyen.authservice.dto.request.RegistrationRequest;
import com.ercanbeyen.authservice.dto.response.MessageResponse;
import com.ercanbeyen.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationRequest request) {
        String message = authService.registerUser(request);
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @GetMapping("/token-refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam("token") String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @GetMapping("/validate")
    public ResponseEntity<MessageResponse> validateToken(@RequestParam("token") String token) {
        log.info("We are in AuthController::validateToken");
        authService.validateToken(token);
        MessageResponse response = new MessageResponse("Token is valid");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/roles")
    public ResponseEntity<MessageResponse> updateRoles(@PathVariable("username") String username, @RequestParam("roles") List<String> roles) {
        String message = authService.updateRoles(username, roles);
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/roles")
    public List<String> getRoles(@PathVariable("username") String username) {
        return authService.getRoles(username);
    }

    @GetMapping("/{username}/authorities")
    public List<GrantedAuthority> getAuthorities(@PathVariable("username") String username) {
        return authService.getAuthorities(username);
    }

    @GetMapping("/extract/username")
    public String updateRoles(@RequestParam("token") String token) {
        return authService.extractUsername(token);
    }

    @GetMapping("/check-role")
    public Boolean checkRole(@RequestParam("token") String token, @RequestParam("role") String role) {
        log.info("We are in AuthController::checkRole. Role {}", role);
        return authService.checkRole(token, role);
    }
}
