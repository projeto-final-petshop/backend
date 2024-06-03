package br.com.finalproject.petconnect.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private OffsetDateTime timestamp;

    private int status;

    private String error;

    private String message;

}
