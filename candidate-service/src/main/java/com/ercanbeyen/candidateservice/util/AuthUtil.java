package com.ercanbeyen.candidateservice.util;

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
public class AuthUtil {
    private final AuthServiceClient authServiceClient;

    public void checkUserHasAdminRole(String username) {
        ResponseEntity<Boolean> response = authServiceClient.checkUserRole(username, "ADMIN");
        Boolean body = response.getBody();

        assert Optional.ofNullable(body).isPresent();
        boolean isAdmin = body;

        if (!isAdmin) {
            log.error("Only admins may observe all candidates");
            throw new ResourceForbiddenException(String.format("Username %s is not authorized", username));
        }

        log.info("User {} has admin role", username);
    }

    public void checkLoggedInUser(String candidateUsername, String loggedInUsername) {
        if (!candidateUsername.equals(loggedInUsername)) {
            log.error("Usernames are not matching: {} & {}", candidateUsername, loggedInUsername);
            throw new ResourceForbiddenException(String.format("Username %s is not authorized", loggedInUsername));
        }

        log.info("Usernames are matching");
    }
}
