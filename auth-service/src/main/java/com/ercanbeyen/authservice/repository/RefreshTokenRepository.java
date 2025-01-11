package com.ercanbeyen.authservice.repository;

import com.ercanbeyen.authservice.entity.RefreshToken;
import com.ercanbeyen.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserCredential(UserCredential userCredential);
}
