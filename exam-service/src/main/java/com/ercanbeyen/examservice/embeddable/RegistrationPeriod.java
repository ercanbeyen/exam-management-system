package com.ercanbeyen.examservice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RegistrationPeriod {
    private LocalDateTime beginAt;
    private LocalDateTime endAt;
}
