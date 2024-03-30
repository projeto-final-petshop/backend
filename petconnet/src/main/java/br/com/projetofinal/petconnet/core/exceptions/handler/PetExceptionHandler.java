package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.UnableToDeletePetException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.UnableToRegisterPetException;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
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
public class PetExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro interno no servidor: ", ex);
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Não foi possível salvar Pet
     *
     * @param ex
     *
     * @return
     */
    @ExceptionHandler(UnableToRegisterPetException.class)
    public ResponseEntity<ErrorResponse> handlerUnableToRegisterPetException(UnableToRegisterPetException ex) {
        log.error("Não foi possível salvar pet: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ops, não foi possível salvar os dados do seu pet. Tente novamente mais tarde.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPetNotFoundException(PetNotFoundException ex) {
        log.error("Pet não econtrado: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .message("Ops, não encontramos nenhum Pet com esse ID. " +
                        "Verifique o ID e tente novamente.")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnableToDeletePetException.class)
    public ResponseEntity<ErrorResponse> handlerUnableToDeletePetException(UnableToDeletePetException ex) {
        log.error("Não foi possível remover o Pet selecionado: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ops, não foi possível excluir o Pet selecionado. " +
                        "Tente novamente mais tarde.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


}
