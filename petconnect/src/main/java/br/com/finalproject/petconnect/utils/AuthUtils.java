package br.com.finalproject.petconnect.utils;

import br.com.finalproject.petconnect.exceptions.runtimes.PetServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtils {

    private final JwtService jwtService;
    private final MessageUtil messageUtil;
    private final UserRepository userRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            // Extrai o email do usuário do token JWT
            String userEmail = jwtService.extractEmail(extractToken(authorizationHeader));
            // Busca o usuário pelo email no banco de dados
            return userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao obter usuário do cabeçalho de autorização: {}", e.getMessage());
            throw new PetServiceException("Falha ao obter usuário do cabeçalho de autorização.");
        }
    }

    private String extractToken(String authorizationHeader) {
        try {
            // Remove "Bearer " do token
            return authorizationHeader.substring(7);
        } catch (Exception e) {
            log.error("Falha ao extrair token: {}", e.getMessage());
            throw new PetServiceException("Falha ao extrair token.");
        }
    }
}
