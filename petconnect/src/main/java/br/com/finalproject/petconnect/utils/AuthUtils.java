package br.com.finalproject.petconnect.utils;

import br.com.finalproject.petconnect.exceptions.runtimes.user.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.*;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtils {

    private final JwtService jwtService;
    private final MessageUtil messageUtil;
    private final UserRepository userRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            String userEmail = jwtService.extractEmail(extractToken(authorizationHeader));
            return userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(NOT_FOUND_USER_MESSAGE)));
        } catch (Exception e) {
            log.error(INVALID_TOKEN, e.getMessage());
            throw new PasswordMismatchException(messageUtil.getMessage(INVALID_AUTH_TOKEN_MESSAGE));
        }
    }

    public String extractToken(String authorizationHeader) {
        try {
            return Optional.ofNullable(authorizationHeader)
                    .filter(header -> header.startsWith(BEARER_PREFIX))
                    .map(header -> header.substring(7)) // Remove "Bearer " do token
                    .orElseThrow(() -> new PasswordMismatchException(messageUtil.getMessage(INVALID_AUTH_TOKEN_MESSAGE)));
        } catch (Exception e) {
            log.error(TOKEN_FAILURE, e.getMessage());
            throw new PasswordMismatchException(messageUtil.getMessage(TOKEN_FAILURE_MESSAGE));
        }
    }

}
