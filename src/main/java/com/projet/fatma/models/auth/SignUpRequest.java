package com.projet.fatma.models.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Password is required")
//    @SecurePassword
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;
    private List<String> roles;
}
