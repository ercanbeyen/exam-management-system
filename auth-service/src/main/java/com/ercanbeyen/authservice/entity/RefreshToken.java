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
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    @OneToOne
    @JoinColumn(name = "user_credential_info", referencedColumnName = "username")
    private UserCredential userCredential;

    @Override
    public String toString() {
        return "RefreshToken{" +
                "token='" + token + '\'' +
                ", status=" + status +
                ", userCredential=" + userCredential.getUsername() +
                '}';
    }
}
