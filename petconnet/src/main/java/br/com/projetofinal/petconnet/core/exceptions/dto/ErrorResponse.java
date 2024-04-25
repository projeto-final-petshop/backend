package br.com.projetofinal.petconnet.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int code; // 500
    private HttpStatus status; // Internal Server Error
    private String message; // Erro interno no servidor

}
