package com.ercanbeyen.authservice.repository;

import com.ercanbeyen.authservice.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    Optional<UserToken> findByAccessToken(String accessToken);
    Optional<UserToken> findByRefreshToken(String refreshToken);
    @Query("""
           SELECT t
           FROM UserToken t
           WHERE t.accessToken = :accessToken AND t.status = ACTIVE
           """)
    Optional<UserToken> findByActiveAccessToken(@Param("accessToken") String accessToken);
    @Query("""
           SELECT t
           FROM UserToken t
           WHERE t.refreshToken = :refreshToken AND t.status = ACTIVE
           """)
    Optional<UserToken> findByActiveRefreshToken(@Param("refreshToken") String refreshToken);
    @Query("""
           SELECT t
           FROM UserToken t
           INNER JOIN UserCredential u ON t.userCredential.username = u.username
           WHERE t.userCredential.username = :username AND t.status = ACTIVE
           """)
    List<UserToken> findAllTokensByUsername(@Param("username") String username);
}
