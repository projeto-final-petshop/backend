package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Auth", description = "Responsável por lidar com operações de autenticação, como registro de usuários.")
@Slf4j
@RequestMapping("/auth")
@RestController
@AllArgsConstructor
@CrossOrigin(
        maxAge = 36000,
        allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST})
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtService.generateToken(userDetails);
        final long expiresAt = jwtService.extractExpiration(jwt).getTime();
        final String username = userDetails.getUsername();
        final List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            String grantedAuthorityAuthority = authority.getAuthority();
            roles.add(grantedAuthorityAuthority);
        }

        LoginResponse response = new LoginResponse(jwt, "Bearer", expiresAt, username, loginRequest.getEmail(), roles);
        return ResponseEntity.ok(response);
    }

}