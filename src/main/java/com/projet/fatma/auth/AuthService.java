package com.projet.fatma.auth;

import com.projet.fatma.models.dto.UserInfo;
import com.projet.fatma.models.response.Jwt;
import com.projet.fatma.models.response.UserDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthService {

    Mono<Jwt> login(String username, String password);

    Mono<Jwt> refresh(String refreshToken);

    void logout(String userEmail);

    Mono<String> registerUser(String email, String password, String fullName);

    UserDto getUserByEmail(String email);

    List<UserInfo> getUsers();
}
