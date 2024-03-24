# Petshop Backend

Esta branch contém o backend para um petshop, desenvolvido em Java Spring Boot.

### Pré requisitos

* Java 21
* Maven 3.8.6
* Spring 3.2.3
* MySQL 8.0
* OpenAPI (Swagger) 3.0.3

### Tecnologias

* **Spring Boot**: Framework de desenvolvimento web completo e robusto.
* **Spring Security**: Segurança da API com autenticação e autorização.
* **Spring Data JPA**: Acesso a dados com JPA e mapeamento de entidades.
* **MySQL**: Banco de dados relacional confiável e escalável.
* **Lombok**: Simplifica o código Java com anotações.
* **MapStruct**: Conversão de objetos entre diferentes classes.
* **Swagger**: Documentação da API interativa e completa.

### Instalação

1. Clone o repositório

  ```
  git clone https://github.com/projeto-final-petshop/petshop-backend.git
  ```

2. Importe o projeto para o seu IDE (IntelliJ IDEA, Eclipse, VSCode etc.)
3. Configure o banco de dados MySQL
4. Execute o comando
   
  ```
  mvn clean install
  ```

4. Inicie o servidor

  ```
  mvn spring-boot:run
  ```

### Acesse a aplicação

A aplicação estará disponível em `http://localhost:8888/api/petshop`.

### Documentação

A documentação da API está disponível em  `http://localhost:8888/api/petshop/swagger-ui.html`.

### Licença

Este projeto está licenciado sob a Apache License.
