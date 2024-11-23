package com.ercanbeyen.schoolservice.advice;

import com.ercanbeyen.servicecommon.client.exception.BadRequestException;
import com.ercanbeyen.servicecommon.client.exception.GlobalErrorCode;
import com.ercanbeyen.servicecommon.client.exception.ErrorResponse;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SchoolServiceGlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, GlobalErrorCode.SCHOOL_BAD_REQUEST_ERROR, exception.getMessage());
        log.error("SchoolServiceGlobalExceptionHandler::handleBadRequestException exception caught {}", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, GlobalErrorCode.SCHOOL_NOT_FOUND_ERROR, exception.getMessage());
        log.error("SchoolServiceGlobalExceptionHandler::handleResourceNotFoundException exception caught {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, GlobalErrorCode.SCHOOL_GENERIC_ERROR, exception.getMessage());
        log.error("SchoolServiceGlobalExceptionHandler::handleGenericException exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(errorResponse);
    }
}
