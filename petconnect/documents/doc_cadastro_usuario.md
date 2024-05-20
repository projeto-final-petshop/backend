Sistema de Cadastro de Usuário robusto e escalável utilizando Arquitetura DDD (Domain-Driven Design) em Java Spring

### Estrutura dos Pacotes

* `dominio`: Entidades, agregados e interfaces de serviço (regras de negócio)
* `aplicacao`: Serviços que implementam as regras de negócio e se comunicam com o repositório
* `infraestrutura`: Adaptadores para banco de dados, validação, etc.
* `web`: Controladores REST e demais elementos da camada de apresentação (opcional)

### Camadas

* Domínio
* Aplicação
* Infraestrutura

### Entidades e Agregados

1. Entidades
    * `Usuario`
        * `id`(Long): Identificação única (gerada automaticamente).
        * `nomeCompleto` (String): Nome completo do usuário.
        * `email` (String): Endereço de email (único e válido).
        * `cpf` (String): Número de CPF (único e válido).
        * `telefone` (String): Número de telefone (formato válido).
        * `senha` (String): Senha segura (criptografada).
        * `dataNascimento`: Data de nascimento no formato `dd/MM/yyyy` (LocalDate).
        * `dataCadastro` (LocalDateTime): Data de cadastro do usuário (atribuída automaticamente).
        * Métodos:
            * `validar()`: Valida as regras de negócio do usuário.
            * `getSenhaCriptografada()`: Criptografa a senha antes de persistir.
    * `Endereco` (opcional)
        * `logradouro`: Logradouro (String).
        * `numero`: Número (String).
        * `complemento`: Complemento (String).
        * `cep`: CEP (String).
        * `cidade`: Cidade (String).
        * `estado`: Estado (String).
        * `pais`: País (String).
2. Agregados
    * `Usuario`: A raiz do agregado, gerenciando as informações e comportamentos do usuário.
    * Pode ser útil se precisar agrupar comportamentos relacionados ao usuário, como autenticação ou envio de emails.

### Repositórios

* Crie interfaces para cada entidade, estendendo `JpaRepository`:
    * `UsuarioRepository`: Interface para persistir e recuperar entidades Usuario.
    * `EnderecoRepository` (opcional): Interface para persistir e recuperar entidades Endereco.
* Utilize métodos prontos do `JpaRepository` para realizar operações CRUD (salvar, buscar, atualizar, remover).

### Serviços

* Crie classes de serviço para encapsular a lógica de negócio:
    * `UsuarioService`:
        * `registerUser(User user)`:
            * Valida os dados do usuário (email, CPF, telefone, senha)
            * Verifica se o email e o CPF já existem.
            * Criptografa a senha antes de persistir.
            * Salva o usuário e o endereço (se existir).
        * `buscarUsuarioPorId(Long id)`: Busca um usuário por ID.
        * `atualizarUsuario(Usuario usuario)`: Atualiza os dados do usuário, validando novamente e criptografando a
          senha.
        * `deletarUsuario(Long id)`: Remove o usuário por ID.
        * `recuperarSenha(String email)`: Envia um email com instruções para recuperação de senha.
    * `EnderecoService`(se necessário): Serviços para gerenciar endereços.
        * `salvarEndereco(Endereco endereco, Usuario usuario)`: Salva o endereço de um usuário.

### Validação

* Utilize a biblioteca `Hibernate Validator` para validar os dados do usuário.
* Anotações:
    * `@Email`: Valida o formato de email.
    * `@NotBlank`: Valida se o campo não está vazio ou em branco.
    * `@Size`: Valida o tamanho mínimo e máximo da senha.
    * `@Pattern`: Valida o formato de CPF e telefone usando expressões regulares fornecidas.
* O Spring validará automaticamente os dados antes de persistir ou atualizar um usuário.
* Implemente validações customizadas no serviço `UsuarioService` para verificar se o email e CPF já existem no banco de
  dados:
    * Utilize o repositório `UsuarioRepository` para buscar usuários por email e CPF.
    * Lance uma exceção caso o email ou CPF já esteja cadastrado.

### Formatação e Validação do CPF

1. Formatação de CPF: O CPF deve ser formatado adequadamente, seguindo o padrão XXX.XXX.XXX-XX.
2. Validação de CPF: Verificar se o CPF é válido utilizando o algoritmo de validação de CPF.
3. Correção Automática: Permitir que o usuário insira o CPF sem pontuações e corrigir automaticamente para o formato
   adequado.
4. Validação de Existência: Verificar se o CPF é válido de acordo com as regras da Receita Federal do Brasil.

### Segurança

* Utilize uma biblioteca de criptografia como o `BCryptPasswordEncoder` para criptografar as senhas antes de persistir
  no banco de dados.
* Armazene apenas o hash da senha no banco.
* Nunca armazene senhas em texto puro.
* Implemente autenticação e autorização para proteger o acesso aos recursos da aplicação.
* Durante a autenticação, compare a senha digitada pelo usuário com o hash armazenado usando o mesmo algoritmo de hash.

### Recuperação de Senha

* Implemente uma funcionalidade para recuperar a senha:
    * O usuário informa o email cadastrado.
    * Verifique se o email existe no banco de dados.
    * Gere um token de recuperação aleatório.
    * Envie um email ao usuário com o link para redefinir a senha, incluindo o token.
    * Quando o usuário acessar o link, verifique o token e permita a definição de uma nova senha.
    * Armazene a nova senha criptografada.

### Testes

* Escreva testes unitários para garantir o funcionamento correto das entidades, repositórios e serviços.
* Utilize frameworks como o JUnit 5 e o Mockito para mockar dependências e testar o isolamento do código.

### Banco de Dados

* Crie as tabelas `usuario` e `endereco` no banco de dados MySQL com as colunas mapeadas para os atributos das
  entidades.
* Utilize o `Spring Data JPA` para mapear as entidades para as tabelas do banco de dados.

---

1. Entidade
    * `UserEntity`: Representa o usuário no sistema.
    * A entidade `UserEntity` representa o modelo de domínio do usuário no sistema. Ela deve encapsular os atributos e
      comportamentos essenciais de um usuário.
2. DTOs
    * Os DTOs (Data Transfer Objects) servem para transportar dados entre diferentes camadas da aplicação.
    * `UserRequest`: Recebe os dados do usuário a partir da requisição.
    * `UserResponse`: Retorna as informações do usuário após o cadastro.
3. Repositórios
    * O repositório é responsável por persistir as entidades UserEntity no banco de dados.
    * `UserRepository`: Persiste os dados do usuário no banco de dados MySQL.
4. Serviços
    * `UserService`: Orquestra o processo de cadastro de usuário, validando dados, mapeando entre entidades e DTOs e
      salvando no banco de dados.
    * O serviço encapsula a lógica de negócio do cadastro de usuário. Ele deve:
        * Validar os dados do usuário (`UserRequest`).
        * Verificar se o email já existe no banco de dados (`UserRepository.existsByEmail`).
        * Criar uma nova instância de `UserEntity` a partir dos dados do DTO.
        * Salvar a entidade no banco de dados (`UserRepository.save`).
        * Mapear a entidade salva para o DTO `UserResponse`.
5. Mapeamento
    * `UserMapper`: Utiliza MapStruct para mapear entre entidades e DTOs de forma automática.
6. Funcionalidade:
    * `Cadastro de Usuário`: Permite que novos usuários se registrem no sistema.