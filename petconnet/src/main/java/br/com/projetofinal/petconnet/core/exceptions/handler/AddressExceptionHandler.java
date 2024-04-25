package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorStatus;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressValidationException;
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
public class AddressExceptionHandler {

    @ExceptionHandler(AddressValidationException.class)
    public ResponseEntity<ErrorResponse> handlerAddressValidationException(AddressValidationException ex) {
        log.error("Dados inválidos: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(400)
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorStatus.ADDRESS_VALIDATION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerAddressNotFoundException(AddressNotFoundException ex) {
        log.error("Endereço não encontrado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .code(404)
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorStatus.ADDRESS_NOT_FOUND.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
