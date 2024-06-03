package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.CustomUserDetails;
import br.com.finalproject.petconnect.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Cadastro e Autenticação de Usuário")
@Slf4j
@RequestMapping("/auth")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserRequest) {
        User registeredUser = authenticationService.signup(registerUserRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        UserDetails userDetails = new CustomUserDetails(authenticatedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}