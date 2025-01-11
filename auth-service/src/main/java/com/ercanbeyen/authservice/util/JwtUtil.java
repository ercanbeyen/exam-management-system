package com.ercanbeyen.authservice.util;

import com.ercanbeyen.authservice.exception.InvalidUserCredentialException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.Optional;

@UtilityClass
public class JwtUtil {
    public Date calculateExpirationDate (int validMinutes) {
        return new Date(System.currentTimeMillis() + 1000L * 60 * validMinutes);
    }

    public String extractTokenFromHeader(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");

        if (Optional.ofNullable(token).isPresent() && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new InvalidUserCredentialException("Token does not exist");
        }

        return token;
    }
}
