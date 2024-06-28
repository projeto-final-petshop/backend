package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest registerUserDto) {
        UserResponse registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            log.info("Tentando autenticar o usuário: {}", loginRequest.getEmail());
            User authenticatedUser = authenticationService.authenticate(loginRequest);
            log.info("Usuário autenticado com sucesso: {}", loginRequest.getEmail());

            String jwtToken = jwtService.generateToken(authenticatedUser);
            log.info("Token JWT gerado para o usuário: {}", loginRequest.getEmail());

            LoginResponse loginResponse = authenticationService.createLoginResponse(authenticatedUser, jwtToken);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("Erro durante a autenticação do usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}