package br.com.finalproject.petconnect.utils.constants;

public class ConstantsUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    // MESSAGES UTIL
    public static final String INVALID_AUTH_TOKEN_MESSAGE = "badRequest.invalidAuthToken";
    public static final String TOKEN_FAILURE_MESSAGE = "tokenFailure";

    public static final String NOT_FOUND_USER_MESSAGE = "notFound.user";
    public static final String NOT_FOUND_ROLE_MESSAGE = "notFound.role";
    public static final String NOT_FOUND_CPF_MESSAGE = "notFound.cpf";
    public static final String NOT_FOUND_EMAIL_MESSAGE = "notFound.email";
    public static final String NOT_FOUND_PET_MESSAGE = "notFound.pet";
    public static final String NO_INACTIVE_USERS_FOUND_MESSAGE = "notFound.noInactiveUsersFound";

    public static final String NO_SEARCH_CRITERIA_PROVIDED_MESSAGE = "badRequest.noSearchCriteriaProvided";

    // LOGS
    public static final String SERVER_ERROR = "Erro no servidor ao tentar processar a requisição: {}";

    public static final String INVALID_TOKEN = "Falha ao obter usuário do cabeçalho de autorização: {}";
    public static final String TOKEN_FAILURE = "Falha ao extrair token: {}";

}
