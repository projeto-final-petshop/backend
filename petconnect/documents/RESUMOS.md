# Pet Connect

## Resumo sobre o arquivo `application.yaml`

O arquivo `application.yaml` configura diversos aspectos da aplicação PetConnect, desde a porta do servidor até as
configurações de internacionalização e envio de emails.

Alguns destaques incluem a porta do servidor (`8888`), a conexão com o banco de dados MySQL (`localhost:3306`), a
formatação de data e hora no padrão brasileiro (`dd/MM/yyyy`) e o envio de emails através do Gmail.

Além disso, o arquivo configura o Spring Data JPA para gerar automaticamente o schema do banco de dados (DDL) e define
paginação padrão para as consultas realizadas pela aplicação (20 itens por página).

### Configurações

**Servidor e Servlets**

* A aplicação é executada na porta `8888`.
* O contexto da aplicação para servlets é definido como `/api/v1`.

**Configurações do Spring**

* **Nome da Aplicação:** `petconnect`
* **Desenvolvimento:** O modo de desenvolvimento está desabilitado (`add-properties: false`).
* **Banner:** Personaliza a mensagem exibida na inicialização da aplicação (arquivo `banner.txt` no classpath).
* **Banco de Dados:**
    * Conexão com MySQL em `localhost:3306` com nome de banco de dados `petconnect`.
    * Usuário e senha do banco de dados: `root` e `root`.
    * Geração automática do schema do banco de dados (DDL) está ativada.
    * Dialect do Hibernate configurado para MySQL.
* **Jackson:**
    * Define a localidade como `pt-BR` e o fuso horário como `America/Sao_Paulo`.
    * Inclui apenas propriedades não nulas por padrão na serialização JSON.
    * Personaliza a base de nomes das mensagens e codificação de caracteres.
* **Mensagens:**
    * Define o arquivo de mensagens como `messages` e a codificação UTF-8.
    * Desabilita o fallback para a localidade do sistema.
* **MVC:**
    * Define a localidade padrão como `pt` e o formato de data e hora no padrão brasileiro.
    * Configura o tamanho padrão da página para consultas (20 itens por página) e limites máximos.
* **Email:**
    * Configura o envio de emails através do Gmail:
        * Usuário: `petshop.petconnect@gmail.com`
        * Senha: `zrgh nfow yzlb eext`
        * Porta: 587
        * Autenticação habilitada
        * TLS obrigatório
        * Nome do remetente: `PetConnect`
    * Desabilita o teste de conexão.
* **Thymeleaf:**
    * Define o prefixo para templates como `classpath:/templates/` e a sufixo `.html`.
    * Configura o modo HTML5 e a codificação UTF-8.
    * Desabilita o cache de templates.
* **Paginação:**
    * Define configurações padrão para paginação de consultas:
        * Tamanho da página padrão: 20 itens por página
        * Tamanho máximo da página: 100 itens por página
        * Numeração de páginas iniciando em 0 (zero)
        * Parâmetro de ordenação na URL: `sort`
* **OpenFeign:**
    * Desabilita a configuração automática do Jackson para OpenFeign.
    * Define um timeout de conexão de 5 segundos.
* **Springdoc:**
    * Define tipos de mídia padrão para consumo e produção (`*/*`).
    * Habilita a exibição do atuador e a geração de documentação da API.
    * Substitui respostas genéricas por anotações `@RestControllerAdvice`.
    * Desabilita o cache da documentação.
    * Define o caminho para a documentação da API: `/api-docs`.
    * Habilita a interface gráfica do Swagger UI no caminho `/swagger-ui.html`.
    * Define a ordenação das operações por método.
    * Desabilita o suporte a CSRF e configura cookies e cabeçalhos personalizados.
* **Email de Notificação:**
    * Define o nome do remetente dos emails de notificação como `PetConnect`.
* **Segurança:**
    * **Autenticação JWT:**
        * Define a chave secreta para tokens JWT: `05955a2232d7f31a0a9879c9233e661f6e2923d5334ec67b7a64c79911407afc`.
        * Define o tempo de expiração dos tokens em 1 dia (86.400.000 milissegundos).
* **Feign Client:**
    * Define um cliente Feign nomeado como `viacep-api` para consumir a API do CEP
      viacep: `[https://viacep.com.br/](https://viacep.com.br/)

## Resumo sobre o arquivo `pom.xml`

O arquivo `pom.xml` declara as dependências utilizadas pelo projeto PetConnect. Estas bibliotecas externas fornecem
funcionalidades diversas para a aplicação.

### Categorias principais de dependências

* **Spring Web:** Fornece suporte para desenvolvimento de aplicações web com Spring.
* **Spring Data JPA:** Simplifica o acesso a banco de dados relacionais usando JPA.
* **MySQL Driver:** Permite a conexão com bancos de dados MySQL.
* **Thymeleaf:** Motor de template HTML para desenvolvimento web.
* **SpringDoc Open API:** Gera documentação da API automaticamente.
* **Spring Cloud OpenFeign:** Permite a integração com serviços externos via API.
* **Spring Security:** Fornece recursos de segurança para a aplicação.
* **JJWT:** Biblioteca para implementação de autenticação JWT (Json Web Token).
* **Spring Boot DevTools:** Auxilia no desenvolvimento da aplicação.
* **Lombok:** Melhora a boilerplate code em Java.
* **MapStruct:** Simplifica a conversão entre objetos.
* **Validação:** Fornece suporte para validação de dados.
* **Java Mail Sender:** Permite o envio de emails.
* **Spring Boot Actuator:** Fornece endpoints para monitoramento da aplicação.
* **Testes:** Bibliotecas para testes unitários e de integração.

### Agrupadas por categoria, podemos destacar:

* **Spring Boot Starter:** Dependências prontas do Spring Boot para funcionalidades comuns como desenvolvimento web,
  validação, acesso a banco de dados e envio de emails.
* **Spring Data JPA:** Ferramentas para acesso a banco de dados relacional utilizando JPA e Hibernate.
* **MySQL Driver:** Biblioteca para conexão com banco de dados MySQL.
* **Thymeleaf:** Template Engine para desenvolvimento de interfaces web.
* **SpringDoc Open API:**  Ferramenta para geração de documentação da API REST no formato OpenAPI.
* **Spring Cloud OpenFeign:** Biblioteca cliente para consumo de serviços remotos via API (API Client).
* **JWT:** Bibliotecas para implementação de autenticação utilizando Json Web Token (JWT).
* **Spring Security:** Biblioteca para implementação de segurança na aplicação.
* **Lombok e MapStruct:** Bibliotecas para simplificação do código Java (Lombok) e mapeamento entre objetos (MapStruct).
* **Testes:** Bibliotecas para testes unitários e de integração da aplicação.
