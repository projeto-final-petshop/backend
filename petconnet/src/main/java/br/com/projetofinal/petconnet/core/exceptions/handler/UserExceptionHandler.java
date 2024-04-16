package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorStatus;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.*;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class UserExceptionHandler {

    /**
     * Credenciais inválidas.
     *
     * @param ex
     *
     * @return
     */
    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidCredentialException(InvalidCredentialException ex) {
        log.error("Exception Handler --- Credenciais inválidas: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(400)
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorStatus.INVALID_CREDENTIAL_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Não foi possível remover o usuário.
     *
     * @param ex
     *
     * @return
     */
    @ExceptionHandler(UnableToDeleteUserException.class)
    public ResponseEntity<ErrorResponse> handlerUnableToDeleteUserException(UnableToDeleteUserException ex) {
        log.error("Exception Handler --- Não foi possível remover o usuário: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.UNABLE_TO_DELETE_USER_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Não foi possível atualizar o usuário.
     *
     * @param ex
     *
     * @return
     */
    @ExceptionHandler(UnableToUpdateUserException.class)
    public ResponseEntity<ErrorResponse> handlerUnableToUpdateUserException(UnableToUpdateUserException ex) {
        log.error("Exception Handler --- Não foi possível atualizar o usuário: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.UNABLE_TO_UPDATE_USER_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * 404 - NOT_FOUND
     * <p>
     * Usuário não encontrado. Verifique o username ou ID.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("Exception Handler --- Usuário não encontrado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(404)
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorStatus.USERNAME_NOT_FOUND_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidUserException(InvalidUserException ex) {
        log.error("Usuário inválido: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(400)
                .status(HttpStatus.BAD_REQUEST)
                .message("Campo inválido: verifique se o campo está preenchido corretamente!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ErrorResponse> handlerRequiredFieldException(RequiredFieldException ex) {
        log.error("Campo obrigatório: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(400)
                .status(HttpStatus.BAD_REQUEST)
                .message("Campo obrigatório!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 409 - CONFLICT
     * <p>
     * Username (email) já cadastrado
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        log.error("Exception Handler --- Email já cadastrado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(409)
                .status(HttpStatus.CONFLICT)
                .message(ErrorStatus.USERNAME_ALREADY_EXISTS_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * 409 - CONFLICT
     * <p>
     * Número de documento (CPF) já cadastrado
     */
    @ExceptionHandler(DocumentNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerDocumentNumberAlreadyExistsException(DocumentNumberAlreadyExistsException ex) {
        log.error("Exception Handler --- Número de documento (CPF) já cadastrado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(409)
                .status(HttpStatus.CONFLICT)
                .message(ErrorStatus.DOCUMENT_NUMBER_ALREADY_EXISTS_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * 400 - Bad Request
     * <p>
     * Campo inválido ou não preenchido
     */
    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidFieldException(InvalidFieldException ex) {
        log.error("Exception Handler --- Erro ao cadastrar usuário: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(400)
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorStatus.INVALID_FIELD_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 403 - FORBIDDEN
     * <p>
     * Usuário inativo.
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handlerUnauthorizedAccessException(UnauthorizedAccessException ex) {
        log.error("Exception Handler --- Usuário não autorizado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(403)
                .status(HttpStatus.FORBIDDEN)
                .message(ErrorStatus.UNAUTHORIZED_ACCESS_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * 403 - FORBIDDEN
     * <p>
     * Usuário inativo.
     */
    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<ErrorResponse> handlerInactiveUserException(InactiveUserException ex) {
        log.error("Exception Handler --- Usuário inativo: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(403)
                .status(HttpStatus.FORBIDDEN)
                .message(ErrorStatus.INACTIVE_USER_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * 500 - INTERNAL_SERVER_ERROR
     * <p>
     * Falha ao ativar o usuário.
     * <p>
     * Ativar usuário = activate user
     */
    @ExceptionHandler(FailedToActivateUserException.class)
    public ResponseEntity<ErrorResponse> handlerFailedToActivateUserException(FailedToActivateUserException ex) {
        log.error("Exception Handler --- Erro ao ativar usuário:", ex);
        var errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.FAILED_TO_ACTIVATE_USER_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * 500 - INTERNAL_SERVER_ERROR
     * <p>
     * Falha ao desativar o usuário.
     * <p>
     * Inativar usuário = Inactivate user
     */
    @ExceptionHandler(FailedToInactivateUserException.class)
    public ResponseEntity<ErrorResponse> handlerFailedToInactivateUserException(FailedToInactivateUserException ex) {
        log.error("Exception Handler --- Erro ao desativar usuário:", ex);
        var errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.FAILED_TO_INACTIVATE_USER_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
