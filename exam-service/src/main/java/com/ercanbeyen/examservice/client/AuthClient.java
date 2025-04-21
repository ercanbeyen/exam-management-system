package com.ercanbeyen.examservice.client;

import com.ercanbeyen.servicecommon.client.AuthServiceClient;
import com.ercanbeyen.servicecommon.client.exception.ResourceForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthClient {
    public static final String ROLE_ADMIN = "ADMIN";
    private final AuthServiceClient authServiceClient;

    public void checkUserHasAdminRole(String username) {
        if (!userHasAdminRole(username)) {
            log.error("User does not have {} role", ROLE_ADMIN);
            throw new ResourceForbiddenException(String.format("User %s is not authorized", username));
        }

        log.info("User {} has admin role", username);
    }

    public void checkLoggedInUser(String candidateUsername, String loggedInUsername) {
        if (!candidateUsername.equals(loggedInUsername)) {
            log.error("Usernames are not matching: {} & {}", candidateUsername, loggedInUsername);
            throw new ResourceForbiddenException("Unauthorized access");
        }

        log.info("Notification belongs to user {}", loggedInUsername);
    }

    private boolean userHasAdminRole(String username) {
        ResponseEntity<Boolean> response = authServiceClient.checkUserRole(username, ROLE_ADMIN);
        Boolean body = response.getBody();

        assert Optional.ofNullable(body).isPresent();
        return body;
    }
}
