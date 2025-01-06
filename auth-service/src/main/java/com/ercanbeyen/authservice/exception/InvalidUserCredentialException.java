package com.ercanbeyen.authservice.exception;

public class InvalidUserCredentialException extends RuntimeException {
    public InvalidUserCredentialException(String message) {
        super(message);
    }
}
