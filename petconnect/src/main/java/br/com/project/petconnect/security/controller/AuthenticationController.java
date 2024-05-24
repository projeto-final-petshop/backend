package br.com.project.petconnect.security.controller;

import br.com.project.petconnect.security.dto.LoginRequest;
import br.com.project.petconnect.security.dto.LoginResponse;
import br.com.project.petconnect.security.service.AuthenticationService;
import br.com.project.petconnect.security.service.JwtService;
import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Operações relacionadas a autenticação do Usuário")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Cadastrar Usuário")
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest userRequest) {
        User registeredUser = authenticationService.signup(userRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Login")
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        var loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        return ResponseEntity.ok(loginResponse);
    }

}
