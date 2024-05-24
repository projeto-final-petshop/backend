package br.com.project.petconnect.exceptions;

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

/**
 * Classe de manipulação global de exceções da aplicação.
 * <p>
 * Intercepta exceções lançadas pelos controladores da aplicação e provê uma resposta padronizada no formato
 * ProblemDetail.
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DESCRIPTION = "description";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {

        ProblemDetail errorDetail = null;

        log.error("Erro ao processar requisição", exception);
        log.error("Detalhes do erro: {}", exception.getMessage());

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "Nome de usuário ou senha incorretos!");
            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "A conta está bloqueada!");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "Você não tem autorização para acessar este recurso!");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "A assinatura do JWT é inválida!");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "O token JWT expirou!");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION, "Erro interno desconhecido do servidor.");
        }

        return errorDetail;
    }

}
