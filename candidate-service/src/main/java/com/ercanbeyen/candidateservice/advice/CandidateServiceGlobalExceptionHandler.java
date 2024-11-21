package com.ercanbeyen.candidateservice.advice;

import com.ercanbeyen.servicecommon.client.exception.ErrorResponse;
import com.ercanbeyen.servicecommon.client.exception.GlobalErrorCode;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CandidateServiceGlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, GlobalErrorCode.CANDIDATE_NOT_FOUND_ERROR, exception.getMessage());
        log.error("CandidateServiceGlobalExceptionHandler::handleResourceNotFoundException exception caught {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, GlobalErrorCode.CANDIDATE_GENERIC_ERROR, exception.getMessage());
        log.error("CandidateServiceGlobalExceptionHandler::handleGenericException exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(errorResponse);
    }
}
