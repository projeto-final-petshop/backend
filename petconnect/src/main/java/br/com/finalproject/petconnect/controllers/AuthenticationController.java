package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidCredentialsException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.UserInactiveException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest registerUserDto) {
        UserResponse registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            authenticationService.authenticate(loginRequest);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtService.generateToken(userDetails);
            final long expiresAt = jwtService.extractExpiration(jwt).getTime();
            final String username = userDetails.getUsername();
            final List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var response = new LoginResponse(jwt, "Bearer", expiresAt, username, loginRequest.getEmail(), roles);
            return ResponseEntity.ok(response);
        } catch (UserInactiveException e) {
            log.error("Usuário inativo: {}", e.getMessage());
            throw new UserInactiveException();
        } catch (FieldNotFoundException e) {
            log.error("Usuário não cadastrado: {}", e.getMessage());
            throw new FieldNotFoundException("Email");
        } catch (InvalidCredentialsException e) {
            log.error("Erro interno ao autenticar usuário: {}", loginRequest.getEmail());
            throw new InvalidCredentialsException();
        } catch (InvalidRequestException e) {
            log.error("Erro na requisição: {}", e.getMessage());
            throw new InvalidRequestException("Senha inválida. Preencha corretamente.");
        }
    }

}