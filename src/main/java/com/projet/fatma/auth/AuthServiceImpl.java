package com.projet.fatma.auth;

import com.projet.fatma.configuration.keycloak.KeycloakConfigurationProperties;
import com.projet.fatma.models.dto.UserInfo;
import com.projet.fatma.models.response.Jwt;
import com.projet.fatma.models.response.LoginError;
import com.projet.fatma.models.response.UserDto;
import com.projet.fatma.utils.exception.AccountDisabledException;
import com.projet.fatma.utils.exception.InternalAppException;
import com.projet.fatma.utils.exception.NotFoundException;
import com.projet.fatma.utils.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final WebClient keycloakClient;
    private final Keycloak keycloakAdmin;

    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    public AuthServiceImpl(WebClient keycloakClient, Keycloak keycloak, KeycloakConfigurationProperties keycloakConfigurationProperties) {
        this.keycloakClient = keycloakClient;
        this.keycloakAdmin = keycloak;
        this.keycloakConfigurationProperties = keycloakConfigurationProperties;}

    @Override
    public Mono<Jwt> login(String email, String password) {
        return keycloakClient.post().uri(buildKeyCloakUri())
                .body(BodyInserters.fromFormData("client_id", keycloakConfigurationProperties.getClientId())
                        .with("client_secret", keycloakConfigurationProperties.getClientSecret())
                        .with("username", email)
                        .with("password", password)
                        .with("grant_type",
                                "password")).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> handle4xxClientError(error, "Incorrect email or password"))
                .onStatus(HttpStatusCode::is5xxServerError, handle5xxServerError())
                .bodyToMono(Jwt.class)
                .doOnSuccess(jwt -> log.info("user {} is logged in", email));}

    @Override
    public Mono<Jwt> refresh(String refreshToken) {
        return keycloakClient.post().uri(buildKeyCloakUri())
                .body(BodyInserters.fromFormData("client_id", keycloakConfigurationProperties.getClientId())
                        .with("client_secret", keycloakConfigurationProperties.getClientSecret())
                        .with("refresh_token", refreshToken)
                        .with("grant_type",
                                "refresh_token"))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> handle4xxClientError(error, "Invalid refresh token"))
                .onStatus(HttpStatusCode::is5xxServerError, handle5xxServerError())
                .bodyToMono(Jwt.class);}

    @Override
    public Mono<String> registerUser(String email, String password, String fullName) {
        UserRepresentation userRepresentation =
                buildUserRepresentation(email, password, fullName);
        Response response = keycloakAdmin.realm(keycloakConfigurationProperties.getRealm()).users()
                .create(userRepresentation);
        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new BadRequestException(
                    "User '" + fullName + "' with email '" + email + "' already exists");
        }
        if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            throw new UnauthorizedException("Invalid email or password");}
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            throw new InternalAppException("Internal problem");}
        response.close();
        return Mono.just("user registred successfully");}

    @Override
    public UserDto getUserByEmail(String email) {
        List<UserRepresentation> users =
                keycloakAdmin.realm(keycloakConfigurationProperties.getRealm()).users()
                        .searchByEmail(email, Boolean.TRUE);
        if (users != null && !users.isEmpty()) {
            String emailUser = users.get(0).getEmail();
            String fullNameUser =
                    users.get(0).getAttributes().getOrDefault("fullName", List.of("")).get(0);
            return new UserDto(emailUser, fullNameUser);
        }
        throw new NotFoundException("User [" + email + "] not found");
    }

    public List<UserInfo> getUsers() {
        List<UserRepresentation> allUsers = keycloakAdmin.realm(keycloakConfigurationProperties.getRealm()).users().list();
        return allUsers.stream()
                .map(this::mapToUserInfo)
                .collect(Collectors.toList());
    }

    private UserInfo mapToUserInfo(UserRepresentation userRepresentation) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userRepresentation.getId());
        userInfo.setFirstName(userRepresentation.getFirstName());
        userInfo.setUserName(userRepresentation.getUsername());
        userInfo.setLastName(userRepresentation.getLastName());
        userInfo.setEmail(userRepresentation.getEmail());
        return userInfo;
    }

    private UserRepresentation buildUserRepresentation(String email, String password,
                                                       String fullName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(email);
        String[] nameParts = fullName.split(" ",2);
          String firstName = nameParts[0];
          String lastName = nameParts[1];
          userRepresentation.setFirstName(firstName);
          userRepresentation.setLastName(lastName);
          userRepresentation.setEnabled(Boolean.TRUE);
          userRepresentation.setAttributes(Map.of("fullName", List.of(fullName)));
          CredentialRepresentation passwordCred = new CredentialRepresentation();
          passwordCred.setTemporary(
                  false);
          passwordCred.setType(CredentialRepresentation.PASSWORD);
          passwordCred.setValue(password);
          userRepresentation.setCredentials(List.of(passwordCred));
          return userRepresentation;
    }

    public void logout(String userEmail) {
        try {
            List<UserRepresentation> user =
                    keycloakAdmin.realm(keycloakConfigurationProperties.getRealm()).users().search(userEmail);
            if (!user.isEmpty()) {
                keycloakAdmin.realm(keycloakConfigurationProperties.getRealm()).users()
                        .get(user.get(0).getId()).logout();
            }
        } catch (InternalAppException e) {
            throw new InternalAppException("Internal error while logging out");
        }
    }

    private Function<UriBuilder, URI> buildKeyCloakUri() {
        return builder -> builder.scheme("http").host(keycloakConfigurationProperties.getHost())
                .port(keycloakConfigurationProperties.getPort()).path(
                        "/auth/realms/" + keycloakConfigurationProperties.getRealm() +
                                "/protocol/openid-connect/token").build();

    }

    private Mono<? extends Throwable> handle4xxClientError(
            ClientResponse error, String invalidUserMessage) {
        return
                error.bodyToMono(LoginError.class).flatMap(loginError -> {
                    log.error(loginError.getError());
                    if(loginError.getError_description().equals("Account disabled")){
                        log.error("account disabled");
                        return Mono.error(new AccountDisabledException("Account disabled"));
                    }
                    if (!loginError.getError().toLowerCase().contains("client")) {
                        log.error(invalidUserMessage);
                        return Mono.error(new UnauthorizedException(invalidUserMessage));
                    }
                    return Mono.error(
                            new InternalAppException("Internal server error while trying to login"));});}

    private static Function<ClientResponse, Mono<? extends Throwable>> handle5xxServerError() {
        return error -> {
            return Mono.error(new InternalAppException("InternalAppException"));};}
}
