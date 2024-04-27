package com.projet.fatma.Controller;

import com.projet.fatma.auth.AuthService;
import com.projet.fatma.models.auth.RefreshLoginRequest;
import com.projet.fatma.models.auth.SignInRequest;
import com.projet.fatma.models.auth.SignUpRequest;
import com.projet.fatma.models.dto.UserEmail;
import com.projet.fatma.models.dto.UserInfo;
import com.projet.fatma.models.response.Jwt;
import com.projet.fatma.models.response.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
@CrossOrigin("*")
@Slf4j
public class AuthResource {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Mono<Jwt>> login(@RequestBody SignInRequest signInRequest) {
        Mono<Jwt> jwt = authService.login(signInRequest.getEmail(), signInRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Mono<Jwt>> refreshToken(
            @RequestBody RefreshLoginRequest refreshLoginRequest) {
        Mono<Jwt> jwt = authService.refresh(refreshLoginRequest.getRefreshToken());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        Mono<String> response =
                authService.registerUser(signUpRequest.getEmail(), signUpRequest.getPassword(),
                        signUpRequest.getFullName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody UserEmail userEmail) {
        authService.logout(userEmail.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authService.getUserByEmail(email));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserInfo>> getUsers() {
        return ResponseEntity.ok(authService.getUsers());
    }
}