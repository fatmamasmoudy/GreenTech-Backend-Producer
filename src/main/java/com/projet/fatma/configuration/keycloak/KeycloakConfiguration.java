package com.projet.fatma.configuration.keycloak;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
@Configuration
@RequiredArgsConstructor
public class KeycloakConfiguration {

    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Bean
    Converter<Jwt, Collection<GrantedAuthority>> keycloakGrantedAuthoritiesConverter() {
        return new KeycloakGrantedAuthoritiesConverter(keycloakConfigurationProperties.getClientId());
    }

    @Bean
    Converter<Jwt, Mono<AbstractAuthenticationToken>> keycloakJwtAuthenticationConverter(
            Converter<Jwt, Collection<GrantedAuthority>> converter) {
        return new ReactiveKeycloakJwtAuthenticationConverter(converter);
    }

    @Bean
    Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl("http://" + keycloakConfigurationProperties.getHost() + ":" +
                        keycloakConfigurationProperties.getPort() + "/auth")
                .realm(keycloakConfigurationProperties.getRealm())
                .clientId(keycloakConfigurationProperties.getClientId())
                .clientSecret(keycloakConfigurationProperties.getClientSecret())
                .username(keycloakConfigurationProperties.getAdminUsername())
                .password(keycloakConfigurationProperties.getAdminPassword())
                .build();
    }
}