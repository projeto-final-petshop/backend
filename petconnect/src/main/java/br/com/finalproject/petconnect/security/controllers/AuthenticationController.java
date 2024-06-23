package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.InvalidCredentialsException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserInactiveException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotRegisteredException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest registerUserDto) {
        UserResponse registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            if (Boolean.FALSE.equals(((User) userDetails).getActive())) {
                log.error("Usuário inativo: {}", loginRequest.getEmail());
                throw new UserInactiveException();
            }

            final String jwt = jwtService.generateToken(userDetails);
            final long expiresAt = jwtService.extractExpiration(jwt).getTime();
            final String username = userDetails.getUsername();
            final List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var response = new LoginResponse(jwt, "Bearer", expiresAt, username, loginRequest.getEmail(), roles);
            return ResponseEntity.ok(response);
        } catch (UserNotRegisteredException | UserInactiveException | InvalidCredentialsException e) {
            log.error("Erro na autenticação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao autenticar usuário: {}", loginRequest.getEmail());
            throw new InvalidCredentialsException();
        }
    }

}