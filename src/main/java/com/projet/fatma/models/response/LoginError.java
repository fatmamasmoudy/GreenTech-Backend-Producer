package com.projet.fatma.models.response;

import lombok.Data;

@Data
public class LoginError {

    private String error;
    private String error_description;
}
