package br.com.finalproject.petconnect.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessageEnum {

    // PasswordMismatchException
    INVALID_AUTHENTICATION_TOKEN("Token de autenticação inválido."),
    FAILED_TO_EXTRACT_TOKEN("Falha ao extrair Token"),

    CPF_NOT_FOUND("CPF não encontrado."),
    CPF_ALREADY_EXISTS("CPF já cadastrado."),

    EMAIL_NOT_FOUND("E-mail não encontrado."),
    EMAIL_ALREADY_EXISTS("E-mail já cadastrado."),
    EMAIL_SEND("Erro ao enviar e-mail. Por favor, tente novamente mais tarde."),

    DATA_MODIFICATION_REGISTER("Falha ao cadastrar informação"),
    DATA_MODIFICATION_UPDATE("Falha ao atualizar informação"),
    DATA_MODIFICATION_DELETE("Falha ao excluir informação"),

    ROLE_NOT_FOUND("Role não encontrado."),

    PET_NOT_FOUND("Pet não encontrado."),
    // DataModificationException
    AUTHENTICATION(""),
    AUTHORIZATION(""),
    INVALID_TOKEN(""),
    ACCESS_DENIED(""),

    REQUIRED_FIELD(""),
    PASSWORD_CHANGE(""),
    PASSWORD_MISMATCH(""),
    PASSWORD_RESET_TOKEN_INVALID(""),

    SERVER_ERROR("Erro no servidor ao tentar processar a requisição.");

    private final String message;

}
