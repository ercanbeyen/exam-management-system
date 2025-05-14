package com.ercanbeyen.authservice.service;

import com.ercanbeyen.servicecommon.client.auth.Role;
import com.ercanbeyen.authservice.request.RegistrationRequest;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.exception.UserAlreadyExistException;
import com.ercanbeyen.authservice.repository.UserCredentialRepository;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUserCredential(RegistrationRequest request) {
        UserCredential userCredential = new UserCredential();
        String encryptedPassword = passwordEncoder.encode(request.password());

        userCredential.setUsername(request.candidateDto().username());
        userCredential.setPassword(encryptedPassword);
        userCredential.setRoles(List.of(Role.USER.toString()));

        UserCredential savedUserCredential = userCredentialRepository.save(userCredential);
        log.info("User Credential {} is successfully created", savedUserCredential.getId());
    }

    public String updateUserCredential(String username, UserCredential request) {
        UserCredential userCredential = findByUsername(username);

        if (!username.equals(request.getUsername())) {
            checkUserCredentialByUsername(request.getUsername());
        }

        userCredential.setUsername(request.getUsername());
        userCredential.setRoles(request.getRoles());

        UserCredential savedUserCredential = userCredentialRepository.save(userCredential);
        log.info("User Credential {} is successfully updated", savedUserCredential.getId());

        return "User Credential is successfully updated";
    }

    public List<String> getRoles(String username) {
        return findByUsername(username).getRoles();
    }

    public void checkUserCredentialByUsername(String username) {
        if (userCredentialRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("User already exists");
        }
    }

    public Optional<UserCredential> findUserCredentialByUsername(String username) {
        return userCredentialRepository.findByUsername(username);
    }

    public UserCredential findByUsername(String username) {
        return findUserCredentialByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User credential does not exist"));
    }
}
