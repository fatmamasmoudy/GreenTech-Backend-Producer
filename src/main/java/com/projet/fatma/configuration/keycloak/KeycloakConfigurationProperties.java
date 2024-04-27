package com.projet.fatma.configuration.keycloak;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
public class KeycloakConfigurationProperties {
    @Value("${keycloak.clientId}")
    String clientId;
    @Value("${keycloak.clientSecret}")
    String clientSecret;
    @Value("${keycloak.host}")
    String host;
    @Value("${keycloak.port}")
    Integer port;
    @Value("${keycloak.admin-username}")
    String adminUsername;
    @Value("${keycloak.admin-password}")
    String adminPassword;
    @Value("${keycloak.realm}")
    String realm;

}
