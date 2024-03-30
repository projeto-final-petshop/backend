package br.com.projetofinal.petconnet.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    /**
     * Status: 400 <br> Error: Bad Request
     */
    INVALID_FIELD_EXCEPTION("Dados inválidos. Verifique se os campos estão preenchidos corretamente."),
    /**
     * Status: 403 <br> Error: Forbidden
     */
    INACTIVE_USER_EXCEPTION("Usuário inativo. Ative a conta para atualizar seus dados."),
    /**
     * Status: 404 <br> Error: Not Found
     */
    USERNAME_NOT_FOUND_EXCEPTION("Usuário não encontrado. Verifique o username ou ID."),
    /**
     * Status: 409 <br> Error: Conflict
     */
    USERNAME_ALREADY_EXISTS_EXCEPTION("Username (email) já cadastrado. Tente outro username/email."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    FAILED_TO_ACTIVATE_USER_EXCEPTION("Falha ao ativar o usuário. Tente novamente mais tarde."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    FAILED_TO_INACTIVATE_USER_EXCEPTION("Falha ao desativar o usuário. Tente novamente mais tarde."),
    /**
     * Status: 500 <br> Error: Internal Server Error
     */
    GENERIC_EXCEPTION("Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.");

    private final String mesages;

}
