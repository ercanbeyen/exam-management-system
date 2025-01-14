package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.constant.enums.TokenStatus;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.entity.UserToken;
import com.ercanbeyen.authservice.exception.TokenAlreadyRevokedException;
import com.ercanbeyen.authservice.repository.UserTokenRepository;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public void revokeAllTokensByUserCredential(UserCredential userCredential) {
        List<UserToken> userTokens = userTokenRepository.findAllTokensByUsername(userCredential.getUsername());

        if (userTokens.isEmpty()) {
            log.info("There is no active token for the user");
            return;
        }

        userTokens.forEach(userToken -> userToken.setStatus(TokenStatus.REVOKED));
        userTokenRepository.saveAll(userTokens);
    }

    public void createUserToken(String accessToken, String refreshToken, UserCredential userCredential) {
        UserToken userToken = new UserToken(accessToken, refreshToken, TokenStatus.ACTIVE, userCredential);
        UserToken savedUserToken = userTokenRepository.save(userToken);
        log.info("User Token {} is successfully created", savedUserToken.getId());
    }

    public UserToken findByAccessToken(String token) {
        Optional<UserToken> optionalUserToken = userTokenRepository.findByActiveAccessToken(token);

        if (optionalUserToken.isEmpty()) {
            TokenStatus tokenStatus = getUserTokenStatusByAccessToken(token);

            if (tokenStatus == TokenStatus.REVOKED) {
                log.error("Access token {} has already been revoked", token);
                throw new TokenAlreadyRevokedException("Access token has already been revoked");
            }
        }

        log.info("Access token is successfully found");

        assert optionalUserToken.isPresent();
        return optionalUserToken.get();
    }

    public UserToken findByRefreshToken(String token) {
        Optional<UserToken> optionalUserToken = userTokenRepository.findByActiveRefreshToken(token);

        if (optionalUserToken.isEmpty()) {
            TokenStatus tokenStatus = getUserTokenStatusByRefreshToken(token);

            if (tokenStatus == TokenStatus.REVOKED) {
                log.error("Refresh token {} has already been revoked", token);
                throw new TokenAlreadyRevokedException("Refresh token has already been revoked");
            }
        }

        log.info("Refresh token is successfully found");

        assert optionalUserToken.isPresent();
        return optionalUserToken.get();
    }

    public TokenStatus getUserTokenStatusByAccessToken(String token) {
        return userTokenRepository.findByAccessToken(token)
                .map(UserToken::getStatus)
                .orElseThrow(() -> {
                    log.error("Access token {} is not found", token);
                    return new ResourceNotFoundException("Access token is not found");
                });
    }

    private TokenStatus getUserTokenStatusByRefreshToken(String token) {
        return userTokenRepository.findByRefreshToken(token)
                .map(UserToken::getStatus)
                .orElseThrow(() -> {
                    log.error("Refresh token {} is not found", token);
                    return new ResourceNotFoundException("Refresh token is not found");
                });
    }
}
