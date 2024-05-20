package br.com.project.petconnect.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    /**
     * 400 - Bad Request
     * <p>
     * 401 -
     * <p>
     * 403 - Forbidden
     * <p>
     * 404 - Not Found
     * <p>
     * 409 - Conflict
     * <p>
     * 500 - Internal Server Error
     */
    INVALID_FIELD(400, "Campo inválido"),
    INVALID_ID(400, "Forneça um ID numérico válido."),

    INVALID_PASSWORD(400, "Senha inválida"),
    PASSWORDS_DO_NOT_MATCH(400, "Senhas não conferem"),

    USER_NOT_FOUND(404, "Usuário não encontrado"),
    OWNER_NOT_FOUND(404, "Tutor não encontrado"),
    PET_NOT_FOUND(404, "Pet não encontrado"),
    PETSHOP_NOT_FOUND(404, "PetShop não encontrado"),

    EMAIL_NOT_FOUND(404, "Email não encontrado"),
    EMAIL_ALREADY_REGISTERED(409, "Email já cadastrado"),

    CPF_NOT_FOUND(404, "CPF não encontrado"),
    CPF_ALREADY_REGISTERED(409, "Número de documento já cadastrado"),

    CNPJ_NOT_FOUND(404, "CNPJ não encontrado"),
    CNPJ_ALREADY_REGISTERED(409, "CNPJ já cadastrado"),

    SERVER_ERROR(500, "Ops... Tente novamente mais tarde!");

    private final int httpStatusCode;
    private final String message;

}
