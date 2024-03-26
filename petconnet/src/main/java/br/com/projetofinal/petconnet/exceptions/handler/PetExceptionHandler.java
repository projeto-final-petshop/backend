package br.com.projetofinal.petconnet.exceptions.handler;

import br.com.projetofinal.petconnet.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.exceptions.errors.users.*;
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
        log.error("Credenciais inválidas: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST)
                .message("Ops, parece que suas credenciais estão incorretas. " +
                        "Verifique o username e password e tente novamente.")
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
        log.error("Não foi possível remover o usuário: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ops, não foi possível remover sua conta. Tente novamente mais tarde.")
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
        log.error("Não foi possível atualizar o usuário: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ops, não foi possível atualizar seus dados. Tente novamente mais tarde.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Usuário não encontrado.
     *
     * @param ex
     *
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException ex) {
        log.error("Usuário não encontrado: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .message("Ops, não encontramos nenhum usuário com esse ID. " +
                        "Verifique o ID e tente novamente.")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidUserException(InvalidUserException ex) {
        log.error("Usuário inválido: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST)
                .message("Campo inválido: verifique se o campo está preenchido corretamente!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ErrorResponse> handlerRequiredFieldException(RequiredFieldException ex) {
        log.error("Campo obrigatório: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST)
                .message("Campo obrigatório!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handlerEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        log.error("Campo obrigatório: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST)
                .message("Email já cadastrado!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
