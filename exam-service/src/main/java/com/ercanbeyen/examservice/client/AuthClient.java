package com.ercanbeyen.examservice.client;

import com.ercanbeyen.servicecommon.client.AuthServiceClient;
import com.ercanbeyen.servicecommon.client.auth.Role;
import com.ercanbeyen.servicecommon.client.exception.ResourceForbiddenException;
import com.ercanbeyen.servicecommon.client.message.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthClient {
    private final AuthServiceClient authServiceClient;

    public void checkUserHasAdminRole(String username) {
        String role = Role.ADMIN.getValue();

        if (!userHasAdminRole(username)) {
            log.error("User does not have {} role", role);
            throw new ResourceForbiddenException(ResponseMessage.UNAUTHORIZED_ACCESS);
        }

        log.info("User {} has {} role", username, role);
    }

    public void checkLoggedInUser(String candidateUsername, String loggedInUsername) {
        if (!candidateUsername.equals(loggedInUsername)) {
            log.error("Usernames are not matching: {} & {}", candidateUsername, loggedInUsername);
            throw new ResourceForbiddenException(ResponseMessage.UNAUTHORIZED_ACCESS);
        }

        log.info("Notification belongs to user {}", loggedInUsername);
    }

    private boolean userHasAdminRole(String username) {
        ResponseEntity<Boolean> response = authServiceClient.checkUserRole(username, Role.ADMIN.toString());
        Boolean body = response.getBody();

        assert Optional.ofNullable(body).isPresent();
        return body;
    }
}
