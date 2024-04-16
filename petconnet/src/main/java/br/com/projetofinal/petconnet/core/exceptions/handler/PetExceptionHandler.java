package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorStatus;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.*;
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
        log.error("Erro interno no servidor: {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.GENERIC_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPetNotFoundException(PetNotFoundException ex) {
        log.error("Pet não econtrado: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(404)
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorStatus.PET_NOT_FOUND_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PetRegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerPetRegistrationException(PetRegistrationException ex) {
        log.error("Erro ao cadastrar Pet: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.PET_REGISTRATION_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PetListException.class)
    public ResponseEntity<ErrorResponse> handlerPetListException(PetListException ex) {
        log.error("Erro ao Listar Pet: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.PET_LIST_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PetUpdateException.class)
    public ResponseEntity<ErrorResponse> handlerPetUpdateException(PetUpdateException ex) {
        log.error("Erro ao atualizar pet: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.PET_UPDATE_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PetRemoveException.class)
    public ResponseEntity<ErrorResponse> handlerPetRemoveException(PetRemoveException ex) {
        log.error("Erro ao remover pet: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.PET_REMOVE_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
