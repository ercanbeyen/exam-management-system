package com.ercanbeyen.notificationservice.client;

import com.ercanbeyen.servicecommon.client.exception.ResourceForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthClient {
    public void checkLoggedInUser(String notificationUsername, String loggedInUsername) {
        if (!notificationUsername.equals(loggedInUsername)) {
            log.error("Usernames are not matching: {} & {}", notificationUsername, loggedInUsername);
            throw new ResourceForbiddenException("Notification does not belong to user");
        }

        log.info("Notification belongs to user {}", loggedInUsername);
    }
}
