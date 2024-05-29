# Pet Connect

* Clique [aqui](https://documenter.getpostman.com/view/13771815/2sA3Qqgstd) para acessar a Collection no Postman.

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


## Executando a migração do Flyway

* Desabilitar o flyway: `spring.flyway.enabled=false`
* Criar arquivo na raiz do projeto: `flyway.properties`

Para migrar o banco de dados, execute no terminal: `flyway migrate -configFiles=flyway.properties`

Rollback da migração: `flyway undo -configFiles=flyway.properties`

## Rotas

A API expões rotas onde algumas são acessíveis sem autenticação enquanto outras exigem autenticação.

| Método | Rotas da API | Status de Acesso | Descrição                             |
|:-------|:-------------|:-----------------|:--------------------------------------|
| POST   | /auth/signup | Desprotegida     | Cadastrar um novo usuário             |
| POST   | /auth/login  | Desprotegida     | Autenticar usuário                    |
| GET    | /users/me    | Protegida        | Recuperar o usuário autenticado atual |
| GET    | /users       | Protegida        | Recuperar todos os usuários           |

### Erro de Autenticação

| Erro de Autenticação                | Exceção lançada         | Código de Status HTTP |
|:------------------------------------|:------------------------|:----------------------|
| Credenciais de Login incorretas     | BadCredentialsException | 401 - Unauthorized    |
| Account locked                      | AccountStatusException  | 403 - Forbidden       |
| Not authorized to access a resource | AccessDeniedException   | 403 - Forbidden       |
| Invalid JWT                         | SignatureException      | 401 - Unauthorized    |
| JWT has expired                     | ExpiredJwtException     | 401 - Unauthorized    |

## Dependências utilizadas

* I/O
    * Validation
* OPS
    * Java Mail Sender
    * Spring Boot Actuator
* WEB
    * Spring Web
* SECURITY
    * Spring Security
* DEVELOPER TOOLS
    * Lombok
    * spring ConfigurationProcessor
    * Spring Boot Dev Tools
* SQL
    * Spring Data JPA
    * Driver MySQL
* Outros
    * MapStruct
    * JJWT Json Web Token

## Documentações

* [Swagger OpenAPI](./src/main/resources/static/openapi/petconnect.yaml)
* [Collection](./src/main/resources/static/postman_collection)
* [‘Scripts’ SQL para migração utilizando Flyway](./src/main/resources/db/migration)
* [Flyway Migration](./documents/flyway.md): Controle de versão para o seu banco de dados, permitindo a migração de
  qualquer versão (incluíndo um banco de dados vazio) para a versão mais recente do schema.
* [Spring Security](./documents/security.md): 'Framework' altamente personalizável de autenticação e controle de acesso
  para aplicações Spring.
* [Sentry](./documents/sentry.md)
* [História de Usuário](./documents/HISTORIA_DE_USUARIO.md)
* [Links Utilizados para consultas e estudos](./documents/links.md)

## Monitoramento de Erros

* [Sentry](https://estudante-k0.sentry.io/settings/)