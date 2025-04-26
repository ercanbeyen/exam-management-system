package com.ercanbeyen.candidateservice.client;

import com.ercanbeyen.servicecommon.client.AuthServiceClient;
import com.ercanbeyen.servicecommon.client.auth.Role;
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
    private final AuthServiceClient authServiceClient;

    public void checkUserHasAdminRole(String username) {
        Role role = Role.ADMIN;
        ResponseEntity<Boolean> response = authServiceClient.checkUserRole(username, role.toString());
        Boolean body = response.getBody();

        assert Optional.ofNullable(body).isPresent();
        boolean isAdmin = body;

        if (!isAdmin) {
            log.error("Only {}s may observe all candidates", role.getValue());
            throw new ResourceForbiddenException(String.format("Username %s is not authorized", username));
        }

        log.info("User {} has {} role", username, role.getValue());
    }

    public void checkLoggedInUser(String candidateUsername, String loggedInUsername) {
        if (!candidateUsername.equals(loggedInUsername)) {
            log.error("Usernames are not matching: {} & {}", candidateUsername, loggedInUsername);
            throw new ResourceForbiddenException(String.format("Username %s is not authorized", loggedInUsername));
        }

        log.info("Usernames are matching");
    }
}
