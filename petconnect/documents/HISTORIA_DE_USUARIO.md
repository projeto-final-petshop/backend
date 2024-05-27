# História de Usuário

1. Usuário OK
    * Cadastrar (POST)
    * Campos obrigatórios
    * Validação de campos: e-mail e CPF (únicos e válidos)
    * Mensagem de erro
    * Atualizar (PUT)
2. Acessar o Sistema OK
    * Login: email e senha (POST)
    * Recuperar senha: envio de email com link para redefinição (POST)
    * Detalhes do Usuário (GET)
3. Pet - CUSTOMER OK
    * Cadastrar Pet (POST)
    * Detalhes do Pet (GET)
    * Atualizar dados do Pet (PUT)
    * Excluir Pet (DELETE)
4. Agenda de Consultas Veterinárias - PETSHOP_ADMIN
    * Visualizar consultas agendadas (GET)
    * Agendar nova consulta (POST)
    * Cancelar ou Reagendar consulta (POST / PUT)
5. Agenda de Serviços - PETSHOP_ADMIN
    * Visualizar serviços agendados (GET)
    * Agendar novo serviço (POST)
    * Cancelar ou Reagendar serviços (POST / PUT)
6. Agenda de Serviços - PETSHOP_EMPLOYEE
    * Visualizar serviços agendados (GET)
    * Agendar novo serviço (POST)
    * Cancelar ou Reagendar serviços (POST / PUT)
7. SUPER_ADMIN
    * Pets
    * Usuários
    * Agenda Veterinária
    * Agenda de Serviços