package com.ercanbeyen.authservice.entity;

import com.ercanbeyen.authservice.constant.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tokens")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    @ManyToOne
    @JoinColumn(name = "user_credential_info", referencedColumnName = "username")
    private UserCredential userCredential;

    public UserToken(String accessToken, String refreshToken, TokenStatus status, UserCredential userCredential) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = status;
        this.userCredential = userCredential;
    }
}
