package com.ercanbeyen.examservice.advice;

import com.ercanbeyen.servicecommon.client.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExamServiceGlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, GlobalErrorCode.EXAM_BAD_REQUEST_ERROR, exception.getMessage());
        log.error("ExamServiceGlobalExceptionHandler::handleBadRequestException exception caught {}", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleResourceForbiddenException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, GlobalErrorCode.EXAM_FORBIDDEN_ERROR, exception.getMessage());
        log.error("ExamServiceGlobalExceptionHandler::handleResourceForbiddenException exception caught {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, GlobalErrorCode.EXAM_NOT_FOUND_ERROR, exception.getMessage());
        log.error("ExamServiceGlobalExceptionHandler::handleResourceNotFoundException exception caught {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, GlobalErrorCode.EXAM_GENERIC_ERROR, exception.getMessage());
        log.error("ExamServiceGlobalExceptionHandler::handleGenericException exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(errorResponse);
    }
}
