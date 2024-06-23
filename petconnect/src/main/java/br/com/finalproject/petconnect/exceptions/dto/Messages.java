package br.com.finalproject.petconnect.exceptions.dto;

public class Messages {

    private Messages() {
        // Para impede a instanciação
    }

    public static final String USER_EXCEPTION = "usuário";
    public static final String EMAIL_EXCEPTION = "email";
    public static final String CPF_EXCEPTION = "CPF";
    public static final String ADDRESS_EXCEPTION = "endereço";
    public static final String NAME_EXCEPTION = "nome";
    public static final String PHONE_NUMBER_EXCEPTION = "número de telefone";

    public static final String TO_UPDATE_EXCEPTION = "atualizar";
    public static final String DISABLE_EXCEPTION = "desativar";
    public static final String DELETE_EXCEPTION = "excluir";
    public static final String SEARCH_EXCEPTION = "buscar";
    public static final String LIST_EXCEPTION = "listar";

    public static final String USER_ALREADY_EXISTS = "Usuário já cadastrado.";
    public static final String EMAIL_ALREADY_EXISTS = "Email já cadastrado.";
    public static final String CPF_ALREADY_EXISTS = "CPF já cadastrado.";
    public static final String USER_NOT_FOUND = "Usuário não encontrado.";
    public static final String EMAIL_NOT_FOUND = "Email não encontrado.";
    public static final String CPF_NOT_FOUND = "CPF não encontrado.";
    public static final String PET_NOT_FOUND = "Pet não encontrado.";
    public static final String USER_NOT_AUTHENTICATED = "Usuário não autenticado.";
    public static final String USER_NO_PERMISSION = "Usuário não tem permissão.";
    public static final String ERROR_UPDATING_DATA = "Erro ao atualizar dados.";
    public static final String USER_UPDATED_SUCCESS = "Usuário atualizado com sucesso.";
    public static final String USER_DEACTIVATED_SUCCESS = "Usuário desativado com sucesso.";
    public static final String USER_DELETED_SUCCESS = "Usuário excluído com sucesso.";
    public static final String PET_REGISTERED_SUCCESS = "Pet cadastrado com sucesso.";
    public static final String PET_UPDATED_SUCCESS = "Pet atualizado com sucesso.";
    public static final String PET_DELETED_SUCCESS = "Pet excluído com sucesso.";
    public static final String NO_PET_REGISTERED = "Você não tem nenhum pet cadastrado.";
    public static final String NO_PERMISSION_UPDATE_DELETE_PET = "Você não tem permissão para atualizar/excluir o pet.";
    public static final String LOGIN_ATTEMPT_FAILED = "Tentativa de login: usuário não cadastrado ou usuário desativado.";

}
