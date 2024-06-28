package br.com.finalproject.petconnect.utils;

import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.UnauthorizedException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtils {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            String userEmail = jwtService.extractEmail(extractToken(authorizationHeader));
            return userRepository.findByEmail(userEmail).orElseThrow(() -> new FieldNotFoundException("Usuário"));
        } catch (Exception e) {
            throw new UnauthorizedException("Falha ao obter usuário.");
        }
    }

    public String extractToken(String authorizationHeader) {
        try {
            return Optional.ofNullable(authorizationHeader)
                    .filter(header -> header.startsWith("Bearer "))
                    .map(header -> header.substring(7)) // Remove "Bearer " do token
                    .orElseThrow(() -> new UnauthorizedException("Token de autenticação ausente."));
        } catch (Exception e) {
            throw new UnauthorizedException("Falha ao extrair Token JWT.");
        }
    }

}
