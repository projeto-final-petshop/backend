package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.CpfAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.RegisterUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
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
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Iniciando o processo de registro para o usuário com o email: {}", registerUserRequest.getEmail());
        try {
            User registeredUser = authenticationService.signup(registerUserRequest);
            log.info("Usuário registrado com sucesso: {}", registeredUser.getEmail());
            return ResponseEntity.ok(registeredUser);
        } catch (EmailAlreadyExistsException | CpfAlreadyExistsException ex) {
            log.error("Erro ao registrar usuário: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException ex) {
            log.error("Erro ao registrar usuário: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception ex) {
            log.error("Erro desconhecido ao registrar usuário: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        log.info("Iniciando o processo de autenticação para o usuário com o email: {}",
                loginRequest.getEmail());
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        var loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        log.info("Autenticação bem-sucedida para o usuário: {}. Token gerado: {}",
                authenticatedUser.getEmail(), jwtToken);
        return ResponseEntity.ok(loginResponse);
    }

}
