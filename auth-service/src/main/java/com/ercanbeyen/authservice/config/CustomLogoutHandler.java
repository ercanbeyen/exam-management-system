package com.ercanbeyen.authservice.config;

import com.ercanbeyen.authservice.constant.enums.TokenStatus;
import com.ercanbeyen.authservice.entity.RefreshToken;
import com.ercanbeyen.authservice.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = JwtUtil.extractTokenFromHeader(request);
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token does not exist"));

        refreshToken.setStatus(TokenStatus.REVOKED);
        refreshTokenRepository.save(refreshToken);

        SecurityContextHolder.clearContext();
    }
}
