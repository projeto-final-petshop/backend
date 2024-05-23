# Pet Connect

## Pré Requisitos

* Java 21
* Maven
* Spring
* MySQL

## Ferramentas

* IDE (de preferência IntelliJ) com plugins:
    * Lombok
    * Sonar Lint
* MySQL Workbench
* Postman

## Executando a aplicação

## Entendendo a estrutura de pastas

### Diretório Core

* Localizado em `./src/main/java/br/com/project/petconnect/core`.
* O diretório core contém pastas comuns a todo o projeto, que futuramente podem ser extraídas e transformadas em
  bibliotecas (libs).
* `/exceptions`: Diretório responsável pelo tratamento de erros personalizados da aplicação.
* `/security`: Diretório responsável pela autenticação e autorização.

### Diretório app

* Localizado em `./src/main/java/br/com/project/petconnect/app`.
* O diretório app possui as funcionalidades e entidades de Pet (Animal), Petshop (Loja), User (Usuário), Owner (Tutor) e
  Login.
* Cada pasta possui a seguinte estrutura:
    * `controller`: Contém classes de controlador para gerenciar as solicitações HTTP relacionadas às operações CRUD (
      Criar, Ler, Atualizar e Excluir).
    * `domain`:
        * `dto`: Data Transfer Objects, usados para transferir dados entre as camadas da aplicação.
        * `entities`: Contém classes Java que representam cada entidade do modelo de dados, como User, Pet, PetShop e
          Tutor.
        * `model`: Modelos que representam a estrutura de dados da aplicação, muitas vezes usados para validar e
          processar dados.
    * `mapping`: Contém interfaces que utilizam anotações do MapStruct para fazer a transformação entre entidades e
      DTOs.
    * `repository`:  Interfaces de repositórios que definem as operações CRUD para cada entidade, utilizando anotações
      do Spring Data JPA.
    * `service`: Contém classes de serviço que encapsulam a lógica de negócio, como salvar, buscar, atualizar e excluir
      dados.

## Relacionamento entre Entidades

* **Um Tutor pode ter vários Animais**: A entidade Tutor possui um relacionamento ManyToOne com a entidade Animal, o que
  significa que um tutor pode ter vários animais cadastrados em seu nome.
* **Um PetShop pode ter vários Animais**: A entidade PetShop também possui um relacionamento ManyToOne com a entidade
  Animal, indicando que um PetShop pode cuidar de vários animais.

## Conceitos

* **DTO (Data Transfer Object)**: Um DTO é um objeto que transporta dados entre processos. Em `./domain/dto`, são
  criados para evitar a exposição direta das entidades e para facilitar a transferência de dados entre a camada de
  apresentação e a camada de negócio.
* **Model**: Um Model é uma representação dos dados e suas regras de validação. Em `./domain/model`, são utilizados para
  definir a estrutura e a lógica dos dados manipulados pela aplicação.
* **Entidades**: As entidades são classes Java que representam os objetos principais do seu modelo de dados, diretamente
  relacionadas às tabelas do banco de dados. Elas são definidas na pasta `./domain/entities` e possuem as seguintes
  características:
    * **Atributos**: Cada entidade tem atributos que correspondem às colunas da tabela do banco de dados. Por exemplo,
      uma entidade User pode ter atributos como `id`, `name` e `email`.
    * **Anotações**: Utilizam anotações do JPA (Java Persistence API) para mapear os atributos da classe para as colunas
      do banco de dados. Exemplos de anotações incluem `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, entre
      outras.
    * **Relacionamentos**: Definem os relacionamentos entre as entidades usando anotações
      como` @OneToMany`, `@ManyToOne`, `@OneToOne`, e` @ManyToMany`. Esses relacionamentos especificam como as entidades
      estão conectadas umas às outras no banco de dados.
    * **Construtores e Métodos**: Além dos atributos, as entidades podem ter construtores, `getters`, `setters` e outros
      métodos úteis para manipulação de dados.

### Mapeamento utilizando MapStruct

A pasta `mapping` contém interfaces que utilizam anotações do MapStruct para fazer a transformação entre entidades e
DTOs. O `MapStruct` é uma biblioteca de mapeamento de objetos que automatiza a conversão entre diferentes tipos de
dados, especialmente entre entidades e DTOs. As interfaces no diretório mapping são responsáveis por:

* **Mapear Entidades para DTOs**: Convertendo objetos de entidades para seus correspondentes DTOs para serem usados nas
  camadas de apresentação e serviço.
* **Mapear DTOs para Entidades**: Convertendo objetos DTOs de volta para entidades, permitindo que os dados sejam salvos
  no banco de dados.

Exemplo de uma interface de mapeamento com MapStruct:

```java

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "userName")
    UserDTO toUserDTO(User user);

    @Mapping(source = "userName", target = "name")
    User toUser(UserDTO userDTO);
}

```

### Biblioteca Lombok

A aplicação também utiliza a biblioteca Lombok para gerar automaticamente getters, setters, construtores e outros
métodos utilitários. Lombok reduz o código boilerplate, facilitando a manutenção e leitura das classes.

Exemplo de uso de Lombok em uma entidade:

```java

@Entity
@Table(name = "users")
@Data // Gera getters, setters, toString, equals e hashCode automaticamente
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
}
```

## Como utilizar a aplicação, caso precise fazer alterações

* Na pasta `entities`: no arquivo que contem as entidades deve incluir o novo campo, pois é responsável por persistir os
  dados no banco.
* Na pasta `dto`: o arquivo que contém `Request`, são os arquivos que irão na requisição, ou seja, campos que o usuário
  pretende preencher.
* Na pasta `dto`: o arquivo que contém `Response`, são os arquivos que irão na resposta, ou seja, no retorno. O que quer
  que seja apresentado ao usuário.
* Na pasta `service`: o arquivo que contém `Service`, são arquivos que possuem a lógica, onde deve incluir a validação
  de campo, como verificar se aquele campo está nulo, ou se a informação que o usuário passou já está cadastrado.

---

Observações:

1. Entidade Owner é o dono do animal - funcionalidade que será ativada futuramente.