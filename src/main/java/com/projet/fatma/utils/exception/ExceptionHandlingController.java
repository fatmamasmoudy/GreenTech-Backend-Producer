package com.projet.fatma.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleAuthenticationException(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMainResource());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMainResource());
    }

    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<String> handleAccountDisabledException(AccountDisabledException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMainResource());
    }
}
