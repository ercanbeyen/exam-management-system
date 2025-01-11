package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.constant.enums.TokenStatus;
import com.ercanbeyen.authservice.constant.message.JwtMessage;
import com.ercanbeyen.authservice.dto.request.LoginRequest;
import com.ercanbeyen.authservice.dto.request.RegistrationRequest;
import com.ercanbeyen.authservice.entity.RefreshToken;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.constant.enums.Role;
import com.ercanbeyen.authservice.exception.InvalidUserCredentialException;
import com.ercanbeyen.authservice.exception.UserAlreadyExistException;
import com.ercanbeyen.authservice.repository.RefreshTokenRepository;
import com.ercanbeyen.authservice.repository.UserCredentialRepository;
import com.ercanbeyen.authservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final RefreshTokenRepository refreshTokenRepository;
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

    public void loginUser(LoginRequest loginRequest, HttpServletResponse servletResponse) {
        authenticateUser(loginRequest);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        log.info("Login User Details: {}", userDetails.getUsername());

        Map<String, String> tokens = jwtService.generateTokens(userDetails);

        UserCredential userCredential = userCredentialRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("User credential does not exist"));

        refreshTokenRepository.findByUserCredential(userCredential)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = new RefreshToken(tokens.get(JwtMessage.REFRESH_TOKEN_TOKEN), TokenStatus.ACTIVE, userCredential);
        refreshTokenRepository.save(refreshToken);

        servletResponse.setHeader(JwtMessage.ACCESS_TOKEN, tokens.get(JwtMessage.ACCESS_TOKEN));
        servletResponse.setHeader(JwtMessage.REFRESH_TOKEN_TOKEN, tokens.get(JwtMessage.REFRESH_TOKEN_TOKEN));
    }

    public void refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String token = JwtUtil.extractTokenFromHeader(servletRequest);
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token is not found"));

        if (refreshToken.getStatus() == TokenStatus.REVOKED) {
            throw new RuntimeException("Token has already been revoked");
        }

        log.info("Token is active");
        Map<String, String> tokens = jwtService.refreshToken(token);

        servletResponse.setHeader(JwtMessage.ACCESS_TOKEN, tokens.get(JwtMessage.ACCESS_TOKEN));
        servletResponse.setHeader(JwtMessage.REFRESH_TOKEN_TOKEN, tokens.get(JwtMessage.REFRESH_TOKEN_TOKEN));
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
