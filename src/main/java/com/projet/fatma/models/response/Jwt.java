package com.projet.fatma.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "expires_in")
    private Long expiresIn;
    @JsonProperty(value = "refresh_expires_in")
    private Long refreshExpiresIn;
}