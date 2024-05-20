# Cadastro de Usuário

### Detalhes do Cadastro de Usuário

- Funcionalidades:
    - Formulário de Cadastro
    - Validação dos dados
    - Senha segura
    - Recuperação de senha
- Atributos do Usuário:
    - Identificação única
    - Nome completo
    - Email
    - Número de documento (CPF)
    - Número de telefone
    - Senha e confirmar senha
    - Data de nascimento (`dd/MM/yyyy`)
    - Data de cadastro
- Regras de Negócio:
    - Formato de email válido e único
    - CPF válido e único
    - Formato de telefone válido (`regexp = "^\\+?[1-9]\\d{12,14}$"`)
    - Senha válida (`regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$"`)

### Conhecimento prévio

- Sou desenvolvedora Backend Java Spring
- Tenho um projeto Spring em andamento
- Banco de dados: MySQL

### Ferramentas e bibliotecas

- Java 21
- Maven
- Lombok
- Spring Data JPA
- Spring Configuration Processor
- MySQL Driver
- Hibernate Validator
- JUnit 5
- Mockito
- MapStruct

### Objetivo final

- Projeto de Conclusão de Curso
- Aplicação Web

1. detalhes do cadastro de usuário
    * Funcionalidades: Quais funcionalidades o cadastro de usuário deve ter?
        * Formulário de Cadastro
        * Validação de dados
        * Senha segura
        * Recuperação de senha
    * Atributos do Usuário: Quais informações serão armazenadas para cada usuário?
        * Nome completo
        * Email
        * Número de documento (CPF)
        * Número de telefone
        * Senha
        * Confirmar senha
        * Data de nascimento
    * Regras de negócio: Quais regras o cadastro de usuário deve seguir?
        * Formato de email válido
        * CPF válido e único
        * Senha forte
2. conhecimento prévio
    * Você já possui familiaridade com Java, Spring e DDD? Sim
    * Já tem um projeto Spring em andamento ou vai iniciar um novo? Sim
    * Qual banco de dados você pretende utilizar? MySQL
3. ferramentas e bibliotecas
    * Você prefere utilizar alguma biblioteca específica para DDD no Spring?
    * Já tem alguma ferramenta em mente para persistência de dados? Hibernate, JPA
4. objetivo final
    * Qual o objetivo final da criação do Cadastro de Usuário? Projeto de Conclusão de Curso
    * O Cadastro de Usuário fará parte de uma aplicação web ou mobile? Aplicação Web

Após ter essas informações, podemos seguir estes passos:

1. Modelagem de Domínio:

Criar as entidades que representam os conceitos principais do seu domínio (ex: Usuário, Endereço, etc.).
Definir os atributos, relacionamentos e comportamentos de cada entidade.

2. Camada de Aplicação:

Criar os serviços que encapsulam as regras de negócio do Cadastro de Usuário.
Definir as interfaces dos serviços e suas implementações.

3. Camada de Infraestrutura:

Configurar a persistência de dados utilizando a tecnologia escolhida (ex: JPA, Hibernate).
Implementar os repositórios para acesso aos dados das entidades.

4. Camada de Apresentação (Opcional):

Criar as controllers e views para interagir com o usuário (ex: formulário de cadastro, tela de loginRequest, etc.).
Utilizar uma tecnologia de front-end da sua preferência (ex: React, Angular, etc.).
Recursos Úteis:

DDD em Java Spring: [URL inválido removido]
Exemplo de Cadastro de Usuário com DDD: [URL inválido removido]
Tutoriais Spring: [URL inválido removido]
Documentação DDD: [URL inválido removido]
Lembre-se:

Comece com um modelo simples e vá refatorando conforme a necessidade.
Utilize testes unitários para garantir a qualidade do código.
Mantenha o código limpo e organizado.