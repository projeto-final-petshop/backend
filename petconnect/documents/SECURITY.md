# Security

Estrutura de pastas

* DTO
    * LoginRequest
    * LoginResponse
    * RoleResponse
* Entity
    * Role
    * RoleEnum

## Models

### Login

* Request
    * email: String
    * password: String
* Response
    * token: String
    * expiresIn: long

### Role

* Request
    * id: Long
    * name: RoleEnum
    * description: String
    * createdAt: OffsetDateTime
    * updateAt: OffsetDateTime
* Enum
    * PETSHOP_ADMIN("Administrador do Sistema"),
    * PETSHOP_EMPLOYEE("Funcionário do PetShop"),
    * CUSTOMER("Dono do Pet"),
    * SUPER_ADMIN("Administrador Geral do Sistema")
* Response

# Implementação do token JWT

* A documentação da aplicação está disponível em: [Swagger OpenAPI](openapi.yaml)

* Filtro de autenticação JWT extrai e valida o token do cabeçalho da solicitação
* Executar a aplicação, gerar o JWT e definir o prazo de validação
* Use o JWT gerado para acessar rotas protegidas
* Capture exceções de autenticação para personalizar a resposta enviada ao cliente.

## Rotas

* [POST] /auth/signup: cadastrar um novo usuário - sem autenticação
* [POST] /auth/login: autenticar um usuário - sem autenticação
* [GET] /users/me: recuperar o usuário atutenticado atual - com autenticação
* [GET] /users: recuperar o usuário autenticado atual - com autenticação

## Configurações

### Dependências

* **Spring Web**: para construir Web, incluindo aplicações RESTful usando Spring MVC. Ele usa Apache Tomcat como
  contêiner incorporado padrão.
* **Spring Security**: Permite implementar autenticação e controle baseado em acesso.
* **Spring Data JPA**: Persistir dados em armazenamentos SQL com Java Persistence API usando Spring Data e Hibernate.
* **Driver MySQL para Java**: O driver MySQL permite interagir com um banco de dados a partir de uma aplicação Java.
* **JJWT**: biblioteca para codificar, decodificar e validar o token JWT no aplicativo.

### Conexão com banco de dados

Configuração do aplicativo para se conectar com o banco de dados e realizar a crição das tabelas do banco de dados (
usando o Hibernate nos bastidores).

* Ver arquivo [application.yaml](../application.yaml)

## Implementação

* Para gerenciar detalhes do usuário relacionados à autenticação, Spring Security fornece uma interface
  chamada `UserDetails` com propriedades e métodos que a entidade Usuário deve substituir na implementação.

* O tempo de expiração do token é expresso em milissegundos.

## Configurações de segurança

* Realizar a autenticação encontrando o usuário em nosso banco de dados.
* Gerar um token JWT quando a autenticação for bem-sucedida

## Crie authentication middleware

Para cada solicitação, queremos recuperar o token JWT no cabeçalho `Authorization` e validá-lo:

* Se o token for inválido, rejeite a solicitação se o token for inválido ou continue caso contrário.
* Se o token for válido, extraia o nome de usuário, encontre o usuário relacionado no banco de dados e configure-o no
  contexto de autenticação para poder acessá-lo em qualquer camada de aplicação.

## Configuração do filtro de solicitação do aplicativo

* Não há necessidade de fornecer o Token CSRF, pois será utilizado.
* O caminho da URL da solicitação corresponde `/auth/signup` e `/auth/login` não requer autenticação.
* Qualquer outro caminho de URL de solicitação deverá ser autenticado.
* A solicitção não tem estado, o que significa que cada solicitação deve ser tratada como uma nova, mesmo que venha do
  mesmo cliente ou tenha sido recebida anteriormente.
* Deve usar o provedor de autenticação customizado e deve ser executado antes do middleware de autenticação.
* A configuração do CORS deve permitir apenas solicitações POST e GET.

## Testando a implementação

Para obter o response das solicitações GET `/users/` e `/users/me` deve inserir o token JWT no cabeçalho de autorização
da solicitação

---

## Cadastro de Usuário

### Request

```text
curl --location 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=153318127DA98E95697020C252F55D07' \
--data-raw '{
    "name": "Rian",
    "email": "rian@email.com",
    "password": "P4$$w0rD"
}'
```

### Response

```json
{
  "id": 2,
  "name": "Rian",
  "email": "rian@email.com",
  "password": "$2a$10$hK3n/9/RYZ0DtRCcoTYL.eAeQuPxCszdiPSsj73nIq1OEfQWRN4ty",
  "createdAt": "2024-05-22T18:03:14.243873-03:00",
  "updateAt": "2024-05-22T18:03:14.243873-03:00",
  "enabled": true,
  "authorities": [],
  "username": "rian@email.com",
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "accountNonExpired": true
}
```

## Login

### Request

```text
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=153318127DA98E95697020C252F55D07' \
--data-raw '{
    "email": "rian@email.com",
    "password": "P4$$w0rD"
}'
```

### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWFuQGVtYWlsLmNvbSIsImlhdCI6MTcxNjQxMTgwMiwiZXhwIjoxNzE2NDk4MjAyfQ.VY_EAlMvd6t3lWzdxXBSi5FO4OqJqXMtm0vC6fvCNCs",
  "expiresIn": 86400000
}
```

### Buscar usuário autenticado

### Request

```text
curl --location 'http://localhost:8080/users/me' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWFuQGVtYWlsLmNvbSIsImlhdCI6MTcxNjQxMTgwMiwiZXhwIjoxNzE2NDk4MjAyfQ.VY_EAlMvd6t3lWzdxXBSi5FO4OqJqXMtm0vC6fvCNCs' \
--header 'Cookie: JSESSIONID=153318127DA98E95697020C252F55D07'
```

### Response

```json
{
  "id": 2,
  "name": "Rian",
  "email": "rian@email.com",
  "password": "$2a$10$hK3n/9/RYZ0DtRCcoTYL.eAeQuPxCszdiPSsj73nIq1OEfQWRN4ty",
  "createdAt": "2024-05-22T21:03:14.243873Z",
  "updateAt": "2024-05-22T21:03:14.243873Z",
  "enabled": true,
  "authorities": [],
  "username": "rian@email.com",
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "accountNonExpired": true
}
```

### Lisar todos os usuários autenticados

### Request

```text
curl --location 'http://localhost:8080/users/' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWFuQGVtYWlsLmNvbSIsImlhdCI6MTcxNjQxMTgwMiwiZXhwIjoxNzE2NDk4MjAyfQ.VY_EAlMvd6t3lWzdxXBSi5FO4OqJqXMtm0vC6fvCNCs' \
--header 'Cookie: JSESSIONID=153318127DA98E95697020C252F55D07'
```

### Response

```json
[
  {
    "id": -48,
    "name": "Juliane",
    "email": "julianemaran@gmail.com",
    "password": "$2a$10$eJBIxHuL6000hJZiWtjX/O6I.XTTYDgCKug/NA7RM.DU7B3U3PFsm",
    "createdAt": "2024-05-22T20:51:12.318351Z",
    "updateAt": "2024-05-22T20:51:12.318351Z",
    "enabled": true,
    "authorities": [],
    "username": "julianemaran@gmail.com",
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "accountNonExpired": true
  },
  {
    "id": 2,
    "name": "Rian",
    "email": "rian@email.com",
    "password": "$2a$10$hK3n/9/RYZ0DtRCcoTYL.eAeQuPxCszdiPSsj73nIq1OEfQWRN4ty",
    "createdAt": "2024-05-22T21:03:14.243873Z",
    "updateAt": "2024-05-22T21:03:14.243873Z",
    "enabled": true,
    "authorities": [],
    "username": "rian@email.com",
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "accountNonExpired": true
  }
]
```

---

## Fluxo de Erro

* **Credenciais de login incorretas**: lançadas pela exceção `BadCredentialsException`, devemos retornar o código de
  status HTTP 401.
* **Conta bloqueada**: lançada pela exceção `AccountStatusException`, devemos retornar o código HTTP Status 403.
* **Não autorizado a acessar um recurso**: lançada pela exceção `AccessDeniedException`, devemos retornar o código HTTP
  Status 403.
* **JWT inválido**: lançado pela exceção `SignatureException`, devemos retornar o código HTTP Status 401.
* **JWT expirado**: lançado pela exceção `ExpiredJwtException`, devemos retornar o código de status HTTP 401.

| Authentication Error                | Exception Thrown        | HTTP Status Code |
|:------------------------------------|:------------------------|:-----------------|
| Bad Login Credentials               | BadCredentialsException | 401              |
| Account locked                      | AccountStatusException  | 403              |
| Not authorized to access a resource | AccessDeniedException   | 403              |
| Invalid JWT                         | SignatureException      | 401              |
| JWT has expired                     | ExpiredJwtException     | 401              |

---

## Controle de Acesso baseado em Funções (Roles)

RBAC: Controle de acesso baseado em função

* Usuário: pode acessar suas informações
* Administrador: pode fazer tudo o que a função de Usuário faz e acessar a lista de usuários
* Super Administrador: pode fazer tudo o que a função Admin faz e criar um usuário administrador.

| Rota              | Função                   | Descrição                              |
|:------------------|:-------------------------|:---------------------------------------|
| GET /users/me     | USER, ADMIN, SUPER_ADMIN | Recuperar o usuário autenticado        |
| GET /users/       | ADMIN, SUPER_ADMIN       | Recuperar a lista de todos os usuários |
| GET /pets/{id}    | SUPER_ADMIN              | Criar um novo administrador            |
| GET /pets/{id}    | USER, ADMIN, SUPER_ADMIN | Buscar um Pet por ID                   |
| GET /pets/list    | USER, ADMIN, SUPER_ADMIN | Listar todos os Pets cadastrados       |
| PUT /pets/{id}    | USER, ADMIN, SUPER_ADMIN | Atualizar informações do Pet           |
| DELETE /pets/{id} | USER, ADMIN, SUPER_ADMIN | Excluir um Pet                         |
| POST /pets        | USER, ADMIN, SUPER_ADMIN | Cadastrar um novo Pet                  |

Para proteger as rotas, basta incluir nos métodos do controller:

* `@PreAuthorize("isAuthenticated()")`
* `@PreAuthorize("hasRole('ADMIN')")`
* `@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")`
* `@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)`

---
