package com.projet.fatma.utils.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractAppException {

    public UnauthorizedException(String mainResource) {
        super(HttpStatus.UNAUTHORIZED.value(), mainResource);
    }
}
