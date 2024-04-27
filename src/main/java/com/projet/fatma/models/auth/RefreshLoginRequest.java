package com.projet.fatma.models.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshLoginRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
