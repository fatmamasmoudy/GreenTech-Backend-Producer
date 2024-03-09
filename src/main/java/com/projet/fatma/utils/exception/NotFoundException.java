package com.projet.fatma.utils.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends  AbstractAppException{
    public NotFoundException(String mainResource) {
        super(HttpStatus.NOT_FOUND.value(), mainResource);
    }
}
