package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.dto.ErrorMessages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DESCRIPTION = "description";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            log.error("Erro de credenciais inválidas: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, ErrorMessages.BAD_CREDENTIALS.getMessage());

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            log.error("Erro de status da conta: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, ErrorMessages.ACCOUNT_LOCKED.getMessage());

            return errorDetail;
        }

        if (exception instanceof AccessDeniedException) {
            log.error("Erro de acesso negado: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, ErrorMessages.ACCESS_DENIED.getMessage());

            return errorDetail;
        }

        if (exception instanceof SignatureException) {
            log.error("Erro de assinatura JWT: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, ErrorMessages.INVALID_SIGNATURE.getMessage());

            return errorDetail;
        }

        if (exception instanceof ExpiredJwtException) {
            log.error("Erro de token JWT expirado: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, ErrorMessages.TOKEN_EXPIRED.getMessage());

            return errorDetail;
        }

        log.error("Erro interno desconhecido: {}", exception.getMessage());
        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, ErrorMessages.UNKNOWN_ERROR.getMessage());

        return errorDetail;
    }

}
