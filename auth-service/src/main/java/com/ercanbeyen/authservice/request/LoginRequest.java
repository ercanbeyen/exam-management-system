package com.ercanbeyen.authservice.request;


import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username is mandatory")
        String username,
        @NotBlank(message = "Password is mandatory")
        String password) {

}
