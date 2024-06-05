# Pet Connect

* Clique [aqui](https://documenter.getpostman.com/view/13771815/2sA3Qqgstd) para acessar a Collection no Postman.

* swagger formato json: http://localhost:8888/api/v1/api-docs
* swagger: http://localhost:8888/api/v1/swagger-ui/index.html

## Pré Requisitos

* Language: Java 21
* Project: Apache Maven 3.8.6
* Spring Boot 3.2.6
* Localidade padrão: pt_BR
* Codificação da Plataforma: UTF-8

## Ferramentas

* IDE IntelliJ
* Postman
* Swagger
* MySQL

## Executando a aplicação

* No terminal insira o comando: `mvn spring-boot:run`
* Executar a aplicação pulando os testes: `mvn spring-boot:run -DskipTests`

---

## Check List

### Frontend

Identidade visual única em toda a aplicação (interface)

- [X] Todas as telas possuem mesmo padrão de cores, fontes, imagens e ícones.
- [X] Todas as telas exibem **usuário logado** (nome, perfil) e **opção de logout** (Sair).
- [X] Todas as telas permitem algum grau de **responsividade**.
- [ ] Todas as **mensagens ao usuário seguem o mesmo padrão** da identidade visual da interface, tanto para exibir
  informação (**info**), aviso (**warning**) ou erro (**error**).

### Validação de Dados (input apenas de dados validados)

- [ ] Todas os campos de entrada orientam seu preenchimento.
- [ ] Todas os campos de entrada indicam se são ou não obrigatórios.
- [X] Apenas aceita senhas fortes (exige no mínimo 8 caracteres, variedade de letras maiúsculas e minúsculas, números e
  caractere especial).
- [X] Não permite cadastro de dados únicos duplicados, como CPF, CNPJ, CRM, CREA, e-mail, usuário para login, etc.
- [X] Permite visualizar senha
- [X] Desejável: Permite confirmar senha.
- [X] Desejável: máscara telefone, CPF, CNPJ, CEP, etc.
- [X] Desejável: obtém endereço a partir de CEP válido. (backend)

* Consulta CEP - Correios (https://www.correios.com.br/).
* Consulta ViaCep

### Funcionalidades

- [X] Login permite recuperação de senha
- [X] Login permite recuperação de usuário
- [ ] Habilitar / desabilitar autenticação em 2 fatores (Two-Factor Authentication - 2FA). (desejável)

### Persistência

- [X] Senha é criptografada no Banco de Dados
- [X] **BD Geral**: entidades fortes são nomeadas como substantivos.
- [X] **BD relacional**: entidades estão relacionadas (PK x FK)
- [X] **BD relacional**: diagrama gerado por engenharia reversa (BD implementado).
- [X] Aplicação trata erros de BD: avisa o usuário (BD fora de serviço, dados únicos duplicados, ...).
- [X] **DELETE**: solicita confirmação antes da exclusão de registro
- [ ] **UPDATE**: formulários para edição estão preenchidos com os dados persistidos em BD
    - Diferencia dados editáveis (telefone, endereço, ... ) de dados não editáveis (CPF, CNPJ, ...)
- [X] **UPDATE de senha**: não apresenta a senha criptografada para atualização (campo vazio)
    - Criptografia de HASH é irreversível, logo a senha criptografada não pode ser revertida / recuperada para edição
- [X] UPDATE de senha em tela exclusiva para trocar senha. (desejável)
- [X] Log para auditoria. (desejável)

### Sessão

- [X] **Faz tratamento de restrição de acesso**: telas ou funcionalidade são acessíveis apenas a usuários com a devida
  permissão, ou seja, existe bloqueio de acesso para usuários sem permissão. Nesse caso, o usuário sem permissão é
  redirecionado à tela apropriada (tela inicial ou tela de login).
- [ ] Expira sessão quando não há atividade do usuário logado – existe timeout de inatividade.

---

## Executando a migração do Flyway

* Desabilitar o flyway: `spring.flyway.enabled=false`
* Criar arquivo na raiz do projeto: `flyway.properties`

Para migrar o banco de dados, execute no terminal: `flyway migrate -configFiles=flyway.properties`

Rollback da migração: `flyway undo -configFiles=flyway.properties`

---

## Rotas

| Método | Rotas da API                         | Permissão           | Descrição                                                                                           |
|:-------|:-------------------------------------|:--------------------|:----------------------------------------------------------------------------------------------------|
| POST   | /auth/signup                         | Desprotegida        | Cadastrar um novo usuário                                                                           |
| POST   | /auth/login                          | Desprotegida        | Autenticar usuário                                                                                  |
| POST   | /auth/reset-password                 | Desprotegida        | Solicitar o reset de senha por email                                                                |
| POST   | /reset-password/confirm              | Desprotegida        | Usuário acessa URL enviada no email com o Token.                                                    |
| GET    | /users/me                            | Usuário autenticado | Recuperar o usuário autenticado atual                                                               |
| PUT    | /users/update                        | Usuário autenticado | Atualiza os dados do usuário                                                                        |
| DELETE | /users/delete                        | Usuário autenticado | Desativa a conta do usuário                                                                         |
| POST   | /admins/register                     | Permissão ADMIN     | Cadastrar um novo usuário com permissões: GROOMING, VETERINARIAN ou EMPLOYEE                        |
| GET    | /admins/users/all                    | Permissão ADMIN     | Listar todos os usuários cadastrados por ordem de nome, ativos e inativos.                          |
| GET    | /admins/users/search                 | Permissão ADMIN     | Listar todos os usuários cadastrados, ativos e inativos e buscar usuários por nome, email e/ou cpf. |
| GET    | /admins/appointments/all             | Permissão ADMIN     | Listar todos os agendamentos.                                                                       |
| GET    | /admins/pets/all                     | Permissão ADMIN     | Listar todos os animais de estimação.                                                               |
| POST   | /pets/register                       | Usuário autenticado | Cadastrar animais de estimação.                                                                     |
| GET    | /pets                                | Usuário autenticado | Listar pets do usuário autenticado.                                                                 |
| GET    | /pets/{petId}                        | Usuário autenticado | Buscar pets do usuário autenticado.                                                                 |
| PUT    | /pets/{petId}                        | Usuário autenticado | Atualizar pet do usuário autenticado.                                                               |
| DELETE | /pets/{petId}                        | Usuário autenticado | Excluir pet do usuário autenticado.                                                                 |
| PUT    | /users/update-password               | Usuário autenticado | Atualizar Senha.                                                                                    |
| POST   | /appointments/schedule               | Usuário autenticado | Realizar agendamento de serviços ou consulta veterinária.                                           |
| PUT    | /appointments/update/{appointmentId} | Usuário autenticado | Atualizar ou reagendar serviços ou consulta veterinária.                                            |
| DELETE | /appointments/cancel/{appointmentId} | Usuário autenticado | Cancelar serviços ou consulta veterinária agendada.                                                 |
| GET    | /appointments/all                    | Usuário autenticado | Listar de agendamentos do usuário autenticado.                                                      |
| GET    | /appointments/pets/{petId}           | Usuário autenticado | Listar agendamentos do Pet selecionado, do usuário autenticado.                                     |
| GET    | /appointments/{appointmentId}        | Usuário autenticado | Visualizar detalhes do agendamento.                                                                 |

### Erro de Autenticação

| Erro de Autenticação                | Exceção lançada         | Código de Status HTTP |
|:------------------------------------|:------------------------|:----------------------|
| Credenciais de Login incorretas     | BadCredentialsException | 401 - Unauthorized    |
| Account locked                      | AccountStatusException  | 403 - Forbidden       |
| Not authorized to access a resource | AccessDeniedException   | 403 - Forbidden       |
| Invalid JWT                         | SignatureException      | 401 - Unauthorized    |
| JWT has expired                     | ExpiredJwtException     | 401 - Unauthorized    |

## Documentações

* [Swagger OpenAPI](/src/main/resources/static/openapi/petconnect.yaml)
* [Collection](/src/main/resources/static/postman_collection)
* [‘Scripts’ SQL para migração utilizando Flyway](/src/main/resources/db/migration)
* [Flyway Migration](/documents/flyway.md): Controle de versão para o seu banco de dados, permitindo a migração de
  qualquer versão (incluíndo um banco de dados vazio) para a versão mais recente do schema.
* [Spring Security](/documents/security.md): 'Framework' altamente personalizável de autenticação e controle de acesso
  para aplicações Spring.
* [Sentry](/documents/sentry.md)
* [História de Usuário](/documents/HISTORIA_DE_USUARIO.md)
* [Links Utilizados para consultas e estudos](/documents/links.md)

## Monitoramento de Erros

* [Sentry](https://estudante-k0.sentry.io/settings/)

---