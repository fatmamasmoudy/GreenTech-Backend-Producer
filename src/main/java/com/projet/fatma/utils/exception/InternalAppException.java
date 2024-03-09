package com.projet.fatma.utils.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalAppException extends AbstractAppException {

    public InternalAppException( String mainResource) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(),mainResource);
    }

}
