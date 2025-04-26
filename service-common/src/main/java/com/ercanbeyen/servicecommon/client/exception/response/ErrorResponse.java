package com.ercanbeyen.servicecommon.client.exception.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus httpStatus, String errorCode, String message) {

}
