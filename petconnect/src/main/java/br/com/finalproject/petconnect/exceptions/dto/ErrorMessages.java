package br.com.finalproject.petconnect.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    BAD_CREDENTIALS("O nome de usuário ou senha estão incorretos."),
    ACCOUNT_LOCKED("A conta está bloqueada."),
    ACCESS_DENIED("Você não está autorizado a acessar este recurso."),
    INVALID_SIGNATURE("A assinatura JWT é inválida."),
    TOKEN_EXPIRED("O token JWT expirou."),
    UNKNOWN_ERROR("Erro interno desconhecido.");

    private final String message;

}
