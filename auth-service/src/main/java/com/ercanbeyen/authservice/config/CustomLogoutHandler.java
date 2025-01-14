package com.ercanbeyen.authservice.config;

import com.ercanbeyen.authservice.entity.UserToken;
import com.ercanbeyen.authservice.service.UserTokenService;
import com.ercanbeyen.authservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final UserTokenService userTokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = JwtUtil.extractTokenFromHeader(request);
        UserToken userToken = userTokenService.findByAccessToken(token);
        userTokenService.revokeAllTokensByUserCredential(userToken.getUserCredential());

        SecurityContextHolder.clearContext();
    }
}
