package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.constant.enums.TokenStatus;
import com.ercanbeyen.authservice.constant.message.JwtMessage;
import com.ercanbeyen.authservice.dto.request.LoginRequest;
import com.ercanbeyen.authservice.dto.request.RegistrationRequest;
import com.ercanbeyen.authservice.entity.UserToken;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.exception.InvalidUserCredentialException;
import com.ercanbeyen.authservice.exception.TokenAlreadyRevokedException;
import com.ercanbeyen.authservice.util.JwtUtil;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.servicecommon.client.exception.InternalServerErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserTokenService userTokenService;
    private final UserCredentialService userCredentialService;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    public String register(RegistrationRequest request) {
        userCredentialService.checkUserCredentialByUsername(request.username());
        userCredentialService.createUserCredential(request);

        try {
            createCandidate(request);
        } catch (Exception exception) {
            log.error("AuthService::register exception caught: {}", exception.getMessage());
            throw new InternalServerErrorException("Candidate could not created");
        }

        return "User is successfully registered";
    }

    public void login(LoginRequest loginRequest, HttpServletResponse servletResponse) {
        authenticateUser(loginRequest);

        String username = loginRequest.username();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("Login User Details: {}", userDetails.getUsername());

        Map<String, String> tokens = jwtService.generateTokens(userDetails);
        UserCredential userCredential = userCredentialService.findByUsername(username);

        userTokenService.revokeAllTokensByUserCredential(userCredential);

        String accessToken = tokens.get(JwtMessage.ACCESS_TOKEN);
        String refreshToken = tokens.get(JwtMessage.REFRESH_TOKEN_TOKEN);

        userTokenService.createUserToken(accessToken, refreshToken, userCredential);

        servletResponse.setHeader(JwtMessage.ACCESS_TOKEN, accessToken);
        servletResponse.setHeader(JwtMessage.REFRESH_TOKEN_TOKEN, refreshToken);
    }

    public void refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String refreshToken = JwtUtil.extractTokenFromHeader(servletRequest);
        UserToken userToken = userTokenService.findByRefreshToken(refreshToken);

        log.info("Token is active");
        Map<String, String> tokens = jwtService.refreshToken(refreshToken);

        UserCredential userCredential = userToken.getUserCredential();
        userTokenService.revokeAllTokensByUserCredential(userCredential);

        String accessToken = tokens.get(JwtMessage.ACCESS_TOKEN);

        userTokenService.createUserToken(accessToken, refreshToken, userCredential);

        servletResponse.setHeader(JwtMessage.ACCESS_TOKEN, accessToken);
        servletResponse.setHeader(JwtMessage.REFRESH_TOKEN_TOKEN, refreshToken);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
        log.info("Token is successfully parsed");

        TokenStatus tokenStatus = userTokenService.getUserTokenStatusByAccessToken(token);
        log.info("Access token is successfully found");

        if (tokenStatus == TokenStatus.REVOKED) {
            throw new TokenAlreadyRevokedException("Access token has already been revoked");
        }

        log.info("Token is validated");
    }

    public String updateUserCredential(String username, UserCredential request) {
        return userCredentialService.updateUserCredential(username, request);
    }

    public List<String> getRoles(String username) {
        return userCredentialService.getRoles(username);
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

    public boolean checkUserRole(String username, String role) {
        List<String> roles = getRoles(username);
        return roles.contains(role);
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

    private void createCandidate(RegistrationRequest request) throws URISyntaxException {
        URI uri = new URI("http://localhost:" + 8082 + "/candidates");

        CandidateDto requestedCandidate = new CandidateDto(null, request.username(), request.fullName(), request.age(), request.gender(), request.schoolId());
        restTemplate.postForObject(uri, requestedCandidate, CandidateDto.class);
    }
}
