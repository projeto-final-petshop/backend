package br.com.finalproject.petconnect.utils;

import br.com.finalproject.petconnect.domain.dto.response.LoginResponse;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.UserNotRegisteredException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.InvalidTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.JWTServiceException;
import br.com.finalproject.petconnect.repositories.UserRepository;
import br.com.finalproject.petconnect.security.services.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.BEARER_PREFIX;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtils {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            String userEmail = jwtTokenProvider.extractEmail(extractToken(authorizationHeader));
            return userRepository.findByEmail(userEmail).orElseThrow(() -> new FieldNotFoundException("Usuário"));
        } catch (Exception e) {
            log.error("Falha ao obter usuário do cabeçalho de autorização: {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }

    public String extractToken(String authorizationHeader) {
        try {
            return Optional.ofNullable(authorizationHeader)
                    .filter(header -> header.startsWith(BEARER_PREFIX))
                    .map(header -> header.substring(7)) // Remove "Bearer " do token
                    .orElseThrow(InvalidTokenException::new);
        } catch (Exception e) {
            log.error("Falha ao extrair token: {}", e.getMessage());
            throw new JWTServiceException("Falha ao extrair Token JWT.");
        }
    }

    public LoginResponse buildLoginResponse(UserDetails userDetails, String token) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotRegisteredException("Usuário não encontrado."));

        return new LoginResponse(
                token,
                "Bearer",
                jwtTokenProvider.getExpirationTime(),
                user.getUsername(), user.getEmail(),
                getUserRoles(userDetails));
    }

    private List<String> getUserRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(Object::toString)
                .toList();
    }

}
