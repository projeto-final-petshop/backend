package br.com.projetofinal.petconnet.exceptions.handler;

import br.com.projetofinal.petconnet.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.exceptions.errors.address.AddressNotFoundException;
import br.com.projetofinal.petconnet.exceptions.errors.address.AddressValidationException;
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
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST)
                .message("Campo inválido: verifique se o campo está preenchido corretamente!")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerAddressNotFoundException(AddressNotFoundException ex) {
        log.error("Endereço não encontrado: ", ex);
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .message("Ops, não encontramos nenhum endereço com esse CEP. " +
                        "Verifique o CEP e tente novamente. ")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
