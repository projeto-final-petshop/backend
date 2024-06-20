package br.com.finalproject.petconnect.utils;

import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.BEARER_PREFIX;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtils {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            String userEmail = jwtService.extractEmail(extractToken(authorizationHeader));
            return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            log.error("Falha ao obter usuário do cabeçalho de autorização: {}", e.getMessage());
            throw new PasswordMismatchException("Token de autenticação inválido.");
        }
    }

    public String extractToken(String authorizationHeader) {
        try {
            return Optional.ofNullable(authorizationHeader)
                    .filter(header -> header.startsWith(BEARER_PREFIX))
                    .map(header -> header.substring(7)) // Remove "Bearer " do token
                    .orElseThrow(() -> new PasswordMismatchException("Token de autenticação inválido."));
        } catch (Exception e) {
            log.error("Falha ao extrair token: {}", e.getMessage());
            throw new PasswordMismatchException("Falha ao extrair Token");
        }
    }

}
