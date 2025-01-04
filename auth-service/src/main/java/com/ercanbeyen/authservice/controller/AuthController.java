package com.ercanbeyen.authservice.controller;

import com.ercanbeyen.authservice.dto.AuthRequest;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential userCredential) {
        return authService.registerUser(userCredential);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return authService.generateToken(authRequest.getUsername());
            } else {
                throw new RuntimeException("Invalid access");
            }
        } catch (Exception exception) {
            log.error("Exception message: {}", exception.getMessage());
            throw exception;
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        log.info("We are in AuthController::validateToken");
        authService.validateToken(token);
        return "Token is valid";
    }

    @PutMapping("/{username}/roles")
    public String updateRoles(@PathVariable("username") String username, @RequestParam("roles") List<String> roles) {
        return authService.updateRoles(username, roles);
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
