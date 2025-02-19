package com.safetynet.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class AlertApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public AlertApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}