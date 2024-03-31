package br.com.projetofinal.petconnet.core.exceptions.handler;

import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorResponse;
import br.com.projetofinal.petconnet.core.exceptions.dto.ErrorStatus;
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
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro interno no servidor: ", ex);
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorStatus.GENERIC_EXCEPTION.getMesages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
