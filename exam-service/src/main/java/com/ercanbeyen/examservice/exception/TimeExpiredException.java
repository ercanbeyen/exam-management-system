package com.ercanbeyen.examservice.exception;

public class TimeExpiredException extends RuntimeException {
    public TimeExpiredException(String message) {
        super(message);
    }
}
