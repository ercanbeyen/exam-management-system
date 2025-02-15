package com.ercanbeyen.authservice.dto.request;

public record RegistrationRequest(String username, String password, String fullName, int age, String gender, String schoolName) {

}
