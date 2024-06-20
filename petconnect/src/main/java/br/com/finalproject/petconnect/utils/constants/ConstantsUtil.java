package br.com.finalproject.petconnect.utils.constants;

public class ConstantsUtil {

    private ConstantsUtil() {
    }

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String XSRF_TOKEN = "XSRF-TOKEN";
    public static final String INVALID_TOKEN = "Token inválido ou expirado.";
    public static final String PASSWORD_RESET_TOKEN_INVALID = "Erro ao redefinir senha: ";

    // ERROR - JWT Service
    public static final String EXTRACT_EMAIL_ERROR = "Falha ao extrair email do token.";
    public static final String EXTRACT_CLAIM_ERROR = "Falha ao extrair claim do token.";
    public static final String BUILD_TOKEN_ERROR = "Falha na construção do token.";
    public static final String VALIDATE_TOKEN_ERROR = "Falha na validação do token.";
    public static final String EXTRACT_EXPIRATION_ERROR = "Falha ao extrair data de expiração do token.";
    public static final String EXTRACT_ALL_CLAIMS_ERROR = "Falha ao extrair todas as claims do token.";
    public static final String CHECK_TOKEN_EXPIRATION_ERROR = "Erro interno no servidor durante a verificação da expiração do token.";
    public static final String GENERATE_TOKEN_ERROR = "Erro interno no servidor durante a geração do token.";
    public static final String GENERATE_TOKEN_WITH_CLAIMS_ERROR = "Falha ao gerar token com claims extras.";

    // NOT FOUND
    public static final String ROLE_NOT_FOUND = "Role não encontrada.";
    public static final String USER_NOT_FOUND = "Usuário não encontrada.";
    public static final String CPF_NOT_FOUND = "CPF não encontrado.";
    public static final String EMAIL_NOT_FOUND = "Email não encontrada.";
    public static final String PET_NOT_FOUND = "Pet não encontrada.";
    public static final String PET_NOT_FOUND_FOR_ID = "Pet não encontrado para o ID: {}";

    // FAILED - User Service
    public static final String FAILED_TO_UPDATE_USER = "Falha ao atualizar usuário.";
    public static final String FAILED_TO_DEACTIVE_USER = "Falha ao desativar usuário.";
    public static final String FAILED_TO_DELETE_USER = "Falha ao deletar usuário.";
    // FAILED - Pet Service
    public static final String FAILED_TO_REGISTER_PET = "Falha ao cadastrar Pet. Por favor, tente novamente mais tarde.";
    public static final String FAILED_TO_LIST_PET = "Falha ao listar Pets. Por favor, tente novamente mais tarde.";
    public static final String FAILED_TO_UPDATE_PET = "Falha ao cadastrar Pet. Por favor, tente novamente mais tarde.";
    public static final String FAILED_TO_LIST_ALL_PET = "Falha ao listar todos os Pets. Por favor, tente novamente mais tarde.";

    // ADMIN SEEDER
    public static final String ADMIN_NAME = "PetConnect";
    public static final String ADMIN_EMAIL = "petshop.petconnect@gmail.com";
    public static final String ADMIN_PASSWORD = "P4$$w0rD";
    public static final String ADMIN_CPF = "396.810.991-09";
    public static final String ADMIN_PHONE_NUMBER = "+5521986548329";
    public static final String ADMIN_ADDRESS = "Avenida Brasil";

    // ROLE SEEDER
    public static final String USER_DESCRIPTION = "Dono do animal de estimação (Pet)";
    public static final String ADMIN_DESCRIPTION = "Dono da loja petshop";
    public static final String GROOMING_DESCRIPTION = "Funcionário responsável pelo banho e tosa";
    public static final String VETERINARIAN_DESCRIPTION = "Médico veterinário que realiza atendimento no petshop";
    public static final String EMPLOYEE_DESCRIPTION = "Funcionário do petshop";

    // Password Service
    public static final String ERROR_CHANGING_PASSWORD = "Erro ao alterar a senha.";
    public static final String PASSWRODS_DO_NOT_MATCH = "Senhas não coincidem.";
    public static final String ERROR_UPDATING_PASSWORD = "Erro ao atualizar senha: ";

}
