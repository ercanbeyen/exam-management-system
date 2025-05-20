package com.ercanbeyen.authservice.advice;

import com.ercanbeyen.authservice.exception.InvalidUserCredentialException;
import com.ercanbeyen.authservice.exception.TokenAlreadyRevokedException;
import com.ercanbeyen.authservice.exception.UserAlreadyExistException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.exception.response.ErrorResponse;
import com.ercanbeyen.servicecommon.client.exception.constant.GlobalErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AuthServiceGlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        log.error("AuthServiceGlobalExceptionHandler::handleValidationExceptions exception caught {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return errors;
    }

    @ExceptionHandler({InvalidUserCredentialException.class, UserAlreadyExistException.class})
    public ResponseEntity<ErrorResponse> handleResourceConflictException(Exception exception) {
        log.error("AuthServiceGlobalExceptionHandler::handleResourceConflictException exception caught {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, GlobalErrorCode.AUTH_CONFLICT_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exception) {
        log.error("AuthServiceGlobalExceptionHandler::handleResourceNotFoundException exception caught {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, GlobalErrorCode.AUTH_NOT_FOUND_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler({TokenAlreadyRevokedException.class, ExpiredJwtException.class})
    public ResponseEntity<ErrorResponse> handleTokenExceptions(Exception exception) {
        log.error("AuthServiceGlobalExceptionHandler::handleTokenExceptions exception caught {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.EXPECTATION_FAILED, GlobalErrorCode.AUTH_EXPECTATION_FAILED_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(errorResponse);
    }
}
