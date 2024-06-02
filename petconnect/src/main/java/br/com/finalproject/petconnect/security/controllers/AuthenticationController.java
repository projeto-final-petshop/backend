package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.CpfAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            User registeredUser = authenticationService.signup(registerUserRequest);
            return ResponseEntity.ok(registeredUser);
        } catch (CpfAlreadyExistsException | EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
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

    /**
     * Refresh Token: ao invés de usar apenas o Token JWT, use um par de tokens: um {@code access token} (de curta
     * duração) e um {@code refresh token} (de longa duração). Quando o {@code acess token} expirar, use o
     * {@refresh token} para obter um novo {@code access token}.
     */
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
//        // Valide e verifique o refresh token
//        String requestRefreshToken = request.getRefreshToken();
//
//        // Gere um novo access token se o refresh token for válido
//        String newAccessToken = jwtService.generateTokenFromRefreshToken(requestRefreshToken);
//
//        return ResponseEntity.ok(new JwtResponse(newAccessToken, requestRefreshToken));
//    }

}