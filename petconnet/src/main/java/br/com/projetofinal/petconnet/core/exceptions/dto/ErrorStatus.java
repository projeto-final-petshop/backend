package br.com.projetofinal.petconnet.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // -------------------------------------- ADDRESS -------------------------------------------------------

    /**
     * Status: 400 <br> Error: Bad Request
     */
    ADDRESS_VALIDATION("Campo inválido: verifique se o campo está preenchido corretamente!"),
    /**
     * Status: 404 <br> Error: Not Found
     */
    ADDRESS_NOT_FOUND("Endereço não encontrado. Verifique o ID e tente novamente."),

    // -------------------------------------- USERS -------------------------------------------------------
    /**
     * Status: 400 <br> Error: Bad Request
     */
    INVALID_FIELD_EXCEPTION("Dados inválidos. Verifique se os campos estão preenchidos corretamente."),
    /**
     * Status: 403 <br> Error: Forbidden
     */
    INACTIVE_USER_EXCEPTION("Usuário inativo. Ative a conta para atualizar seus dados."),
    /**
     * Status: 403 <br> Error: Forbidden
     */
    UNAUTHORIZED_ACCESS_EXCEPTION("Usuário não autorizado!"),
    /**
     * Status: 404 <br> Error: Not Found
     */
    USERNAME_NOT_FOUND_EXCEPTION("Usuário não encontrado. Verifique o ID ou username e tente novamente."),
    /**
     * Status: 409 <br> Error: Conflict
     */
    USERNAME_ALREADY_EXISTS_EXCEPTION("Username (email) já cadastrado."),
    /**
     * Status: 409 <br> Error: Conflict
     */
    DOCUMENT_NUMBER_ALREADY_EXISTS_EXCEPTION("Número de documento (CPF) já cadastrado."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    FAILED_TO_ACTIVATE_USER_EXCEPTION("Falha ao ativar o usuário. Tente novamente mais tarde."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    FAILED_TO_INACTIVATE_USER_EXCEPTION("Falha ao desativar o usuário. Tente novamente mais tarde."),
    /**
     * Status: 400 <br> Error: Bad Request
     */
    INVALID_CREDENTIAL_EXCEPTION("Ops, parece que suas credenciais estão incorretas. " +
            "Verifique o username e password e tente novamente."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    UNABLE_TO_DELETE_USER_EXCEPTION("Ops, não foi possível remover sua conta. Tente novamente mais tarde."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    UNABLE_TO_UPDATE_USER_EXCEPTION("Ops, não foi possível atualizar seus dados. Tente novamente mais tarde."),


    // -------------------------------------- PET -------------------------------------------------------

    PET_NOT_FOUND_EXCEPTION("Pet não encontrado. Verifique o ID e tente novamente."),

    PET_REGISTRATION_EXCEPTION("Erro ao Cadastrar Pet. Tente novamente mais tarde."),

    PET_LIST_EXCEPTION("Erro ao Listar Pet. Tente novamente mais tarde."),

    PET_UPDATE_EXCEPTION("Erro ao Atualizar Pet. Tente novamente mais tarde."),

    PET_REMOVE_EXCEPTION("Erro ao Remover Pet. Tente novamente mais tarde."),

    // -------------------------------------- GENERIC -------------------------------------------------------

    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    GENERIC_EXCEPTION("Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.");

    private final String mesages;

}
