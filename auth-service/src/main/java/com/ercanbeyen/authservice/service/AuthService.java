package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public String registerUser(UserCredential userCredential) {
        String encryptedPassword = passwordEncoder.encode(userCredential.getPassword());
        userCredential.setPassword(encryptedPassword);
        userCredential.setRoles(List.of("USER"));
        userCredentialRepository.save(userCredential);
        return "User is added to the system";
    }

    public String generateToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("Generate token User Details: {}", userDetails.getUsername());
        return jwtService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
        log.info("Token is validated");

    }

    public String updateRoles(String username, List<String> roles) {
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow();
        userCredential.setRoles(roles);
        userCredentialRepository.save(userCredential);
        return "Roles of user are updated";
    }

    public List<String> getRoles(String username) {
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow();
        return userCredential.getRoles();
    }

    public List<GrantedAuthority> getAuthorities(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return (List<GrantedAuthority>) userDetails.getAuthorities();
    }

    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    public boolean checkRole(String token, String role) {
        return jwtService.hasRole(token, role);
    }
}
