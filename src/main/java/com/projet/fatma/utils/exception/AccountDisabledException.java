package com.projet.fatma.utils.exception;

import org.springframework.http.HttpStatus;

public class AccountDisabledException extends  AbstractAppException {
    public AccountDisabledException(String mainResource) {
        super(HttpStatus.UNAUTHORIZED.value(), mainResource);
    }
}
