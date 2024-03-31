package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorStatus;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
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

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPetNotFoundException(PetNotFoundException ex) {
        log.error("Pet não econtrado: ", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .message(ErrorStatus.PET_NOT_FOUND_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
