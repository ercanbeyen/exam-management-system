package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.dto.request.LoginRequest;
import com.ercanbeyen.authservice.dto.request.RegistrationRequest;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.constant.enums.Role;
import com.ercanbeyen.authservice.exception.InvalidUserCredentialException;
import com.ercanbeyen.authservice.exception.UserAlreadyExistException;
import com.ercanbeyen.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public String registerUser(RegistrationRequest request) {
        boolean userExists = userCredentialRepository.existsByUsername(request.username());

        if (userExists) {
            throw new UserAlreadyExistException("User already exists");
        }

        UserCredential userCredential = new UserCredential();
        String encryptedPassword = passwordEncoder.encode(request.password());

        userCredential.setUsername(request.username());
        userCredential.setPassword(encryptedPassword);
        userCredential.setEmail(request.email());
        userCredential.setRoles(List.of(Role.USER.toString()));

        userCredentialRepository.save(userCredential);

        return "User is successfully registered";
    }

    public Map<String, String> loginUser(LoginRequest request) {
        authenticateUser(request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        log.info("Login User Details: {}", userDetails.getUsername());

        return jwtService.generateTokens(userDetails);
    }

    public Map<String, String> refreshToken(String token) {
        return jwtService.refreshToken(token);
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

    private void authenticateUser(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            if (!authentication.isAuthenticated()) {
                throw new InvalidUserCredentialException("Incorrect username or password");
            }

            log.info("User is authenticated");
        } catch (Exception exception) {
            log.error("Exception: {}. Message: {}", exception.getClass(), exception.getMessage());
            throw exception;
        }
    }
}
