package com.ercanbeyen.authservice.util;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class JwtUtil {
    public Date calculateExpirationDate (int validTime) {
        return new Date(System.currentTimeMillis() + validTime);
    }
}
