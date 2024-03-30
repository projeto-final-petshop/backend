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

    private LocalDateTime timestamp; // 2024-03-26T12:24:23.666+00:00
    private int status; // 500
    private HttpStatus error; // Internal Server Error
    private String message; // Erro interno no servidor

}
