package br.com.finalproject.petconnect.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    private int status;
    private String error;
    private String message;
    private List<String> errors;

}
