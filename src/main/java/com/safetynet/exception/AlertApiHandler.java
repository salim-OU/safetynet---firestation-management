package com.safetynet.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@RestControllerAdvice
public class AlertApiHandler {

    @ExceptionHandler(AlertApiException.class)
    public ResponseEntity<ErrorResponse> handleAlertApiException(AlertApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @Getter
    @AllArgsConstructor
    static class ErrorResponse {
        private final LocalDateTime timestamp;
        private final int status;
        private final String message;
    }
}