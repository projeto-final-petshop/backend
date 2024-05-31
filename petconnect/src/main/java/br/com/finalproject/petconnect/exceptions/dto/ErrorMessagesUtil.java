package br.com.finalproject.petconnect.exceptions.dto;

public class ErrorMessagesUtil {

    private ErrorMessagesUtil() {
    }

    // Erros relacionados a Role
    public static final String ROLE_NOT_FOUND = "Role não encontrada.";
    public static final String INVALID_ROLE = "Função inválida para cadastro de usuário.";

    // Erros relacionados a Pet
    public static final String PET_NOT_FOUND = "Pet não encontrado ou não pertence ao usuário.";
    public static final String PET_ALREADY_EXISTS = "Pet já existe.";
    public static final String INVALID_PET_ID = "ID do pet inválido.";

    // Erros relacionados a Usuários
    public static final String USER_NOT_FOUND = "Usuário não encontrado.";
    public static final String USER_ALREADY_EXISTS = "Usuário já cadastrado.";
    public static final String INVALID_USER_ID = "ID de usuário inválido.";

    // Erros relacionados a Autenticação e Autorização
    public static final String INVALID_AUTH_TOKEN = "Token de autenticação inválido.";
    public static final String TOKEN_PROCESSING_FAILURE = "Falha ao processar o token de autenticação.";
    public static final String UNAUTHORIZED_ACCESS = "Acesso não autorizado.";
    public static final String EXPIRED_TOKEN = "O token de autenticação expirou.";
    public static final String INVALID_CREDENTIALS = "Credenciais inválidas.";
    public static final String AUTHENTICATION_REQUIRED = "Autenticação necessária.";
    public static final String ACCESS_DENIED = "Acesso negado.";
    public static final String INSUFFICIENT_PERMISSIONS = "Permissões insuficientes para executar esta ação.";
    public static final String ADMIN_PERMISSION_ERROR = "Você não tem permissão para realizar esta ação.";

    // Erros relacionados a Agendamento
    public static final String APPOINTMENT_NOT_FOUND = "Agendamento não encontrado ou não pertence ao usuário.";
    public static final String APPOINTMENT_ALREADY_BOOKED = "Este horário já está agendado.";
    public static final String INVALID_APPOINTMENT_DATE = "Data de agendamento inválida.";

    // Erros relacionados a Internal Server Error e Timeout
    public static final String INTERNAL_SERVER_ERROR = "Erro interno do servidor. Por favor, tente novamente mais tarde.";
    public static final String TIMEOUT_ERROR = "Tempo de espera excedido. Tente novamente mais tarde.";

    // Outros erros genéricos
    public static final String RESOURCE_NOT_FOUND = "Recurso não encontrado.";
    public static final String INVALID_INPUT_DATA = "Dados de entrada inválidos. Verifique os campos e tente novamente.";
    public static final String MISSING_REQUIRED_FIELDS = "Campos obrigatórios estão ausentes.";
    public static final String INVALID_JSON_FORMAT = "Formato de JSON inválido na solicitação.";
    public static final String PAGE_NOT_FOUND = "Página não encontrada.";

    // Mensagens de sucesso
    public static final String USER_CREATED_SUCCESSFULLY = "Usuário cadastrado com sucesso.";
    public static final String PET_CREATED_SUCCESSFULLY = "Pet cadastrado com sucesso.";

    // Erros relacionados a Senhas
    public static final String PASSWORDS_DO_NOT_MATCH = "As senhas não conferem.";
    public static final String INCORRECT_PASSWORD = "Senha incorreta.";
    public static final String INVALID_PASSWORD_FORMAT = "Formato de senha inválido.";
    public static final String NEW_PASSWORD_MUST_BE_DIFFERENT = "A nova senha deve ser diferente da senha atual.";

    // Mensagem de sucesso relacionada a senhas
    public static final String RESET_PASSWORD_LINK_SENT = "O link de redefinição de senha foi enviado para seu e-mail.";

    // Mensagens de sucesso relacionadas a senhas
    public static final String PASSWORD_UPDATED_SUCCESSFULLY = "Senha atualizada com sucesso!";
    public static final String PASSWORD_RESET_SUCCESSFULLY = "Reset de senha realizado com sucesso!";

    // Erros relacionados a e-mails
    public static final String EMAIL_SENDING_FAILURE = "Falha ao enviar o e-mail.";
    public static final String EMAIL_ALREADY_IN_USE = "O e-mail fornecido já está em uso.";
    public static final String EMAIL_NOT_FOUND = "E-mail não encontrado.";
    public static final String INVALID_EMAIL_FORMAT = "Formato de e-mail inválido.";
    public static final String EMAIL_SERVICE_UNAVAILABLE = "Serviço de e-mail temporariamente indisponível.";
    public static final String EMAIL_VERIFICATION_REQUIRED = "É necessário verificar o e-mail para concluir o cadastro.";
    public static final String EMAIL_VERIFICATION_LINK_SENT = "Um link de verificação foi enviado para seu e-mail.";

    // Erros relacionados ao envio de email
    public static final String EMAIL_SERVICE = "Erro ao enviar e-mail. Por favor, tente novamente mais tarde.";
    public static final String PASSWORD_RESET_REQUEST = "Solicitação de redefinição de senha.";
    public static final String RESET_YOUR_PASSWORD = "Para redefinir sua senha, clique no link abaixo.\n";
    public static final String INVALID_TOKEN = "Token inválido";
    public static final String TOKEN_EXPIRED = "Token expirado";

    // Erros relacionados ao CPF
    public static final String CPF_ALREADY_REGISTERED = "Este CPF já está registrado.";
    public static final String INVALID_CPF_FORMAT = "Formato de CPF inválido.";
    public static final String CPF_NOT_FOUND = "CPF não encontrado.";

}
