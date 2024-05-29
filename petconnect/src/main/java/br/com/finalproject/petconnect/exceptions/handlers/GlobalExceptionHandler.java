package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.dto.ErrorMessages;
import br.com.finalproject.petconnect.exceptions.dto.ErrorResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.*;
import br.com.finalproject.petconnect.utils.MessageUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountLockedException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DESCRIPTION = "description";

    private final MessageUtil messageUtil;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(Exception exception) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String messageKey = ErrorMessages.UNKNOWN_ERROR.getMessageKey();

        if (exception instanceof BadCredentialsException) {
            log.error("Erro de credenciais inválidas: {}", exception.getMessage());
            status = HttpStatus.UNAUTHORIZED;
            messageKey = ErrorMessages.BAD_CREDENTIALS.getMessageKey();
        }

        if (exception instanceof AccountStatusException || exception instanceof AccessDeniedException ||
                exception instanceof SignatureException || exception instanceof ExpiredJwtException) {
            log.error("Erro de segurança: {}", exception.getMessage());
            status = HttpStatus.FORBIDDEN;
            switch (exception.getClass().getSimpleName()) {
                case "AccountStatusException":
                    messageKey = ErrorMessages.ACCOUNT_LOCKED.getMessageKey();
                    break;
                case "AccessDeniedException":
                    messageKey = ErrorMessages.ACCESS_DENIED.getMessageKey();
                    break;
                case "SignatureException":
                    messageKey = ErrorMessages.INVALID_SIGNATURE.getMessageKey();
                    break;
                case "ExpiredJwtException":
                    messageKey = ErrorMessages.TOKEN_EXPIRED.getMessageKey();
                    break;
                default:
                    break;
            }
        }

        if (exception instanceof InvalidFieldException || exception instanceof NoSearchCriteriaProvidedException ||
                exception instanceof PasswordUpdateException || exception instanceof AuthenticationException ||
                exception instanceof TokenNotFoundException || exception instanceof UnauthenticatedException ||
                exception instanceof InvalidSignatureException || exception instanceof TokenExpiredException ||
                exception instanceof PermissionDeniedException || exception instanceof UserNotActiveException ||
                exception instanceof CpfNotFoundException || exception instanceof EmailNotFoundException ||
                exception instanceof NoPetsFoundException || exception instanceof PetNotFoundException ||
                exception instanceof RoleNotFoundException || exception instanceof UserNotFoundException ||
                exception instanceof NoInactiveUsersFoundException || exception instanceof CpfAlreadyExistsException ||
                exception instanceof EmailAlreadyExistsException || exception instanceof AccountLockedException ||
                exception instanceof PetServiceException || exception instanceof UnknownErrorException) {
            log.error("Erro específico: {}", exception.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            messageKey = "runtime.error";

        }

        var errorResponse = ErrorResponse.builder()
                .title("Erro de segurança")
                .status(status.value())
                .detail(exception.getMessage())
                .type("/error")
                .build();

        errorResponse.setProperty(DESCRIPTION, messageUtil.getMessage(messageKey));

        return ResponseEntity.status(status).body(errorResponse);

    }

}
