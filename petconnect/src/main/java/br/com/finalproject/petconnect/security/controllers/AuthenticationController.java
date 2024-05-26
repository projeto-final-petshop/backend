package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.RegisterUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest registerUserRequest) {
        User registeredUser = authenticationService.signup(registerUserRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
