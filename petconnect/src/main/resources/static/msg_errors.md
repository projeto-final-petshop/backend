### Validação dos Campos

| Campo             | Validação                                                                                                                        | Mensagem de Erro                                                                                                                                                         |
|:------------------|:---------------------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `name`            | `@NotNull`, `@Size(min = 3, max = 250)`                                                                                          | Campo nome é obrigatório. <br> O nome deve ter entre 3 e 250 caracteres.                                                                                                 |
| `email`           | `@NotNull`, `@Email(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$")`                                                   | Campo email é obrigatório. <br> Insira um email válido.                                                                                                                  |
| `password`        | `@NotNull`, `@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")`, `@JsonProperty(WRITE_ONLY)` | Campo senha é obrigatório. / A senha deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial. |
| `confirmPassword` | `@NotNull`, `@JsonProperty(WRITE_ONLY)`                                                                                          | Campo confirmar senha é obrigatório.                                                                                                                                     |
| `cpf`             | `@NotNull`, `@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")`                                                               | Campo CPF é obrigatório. / CPF: Por favor, insira um CPF válido.                                                                                                         |
| `phoneNumber`     | `@Pattern(regexp = "^\\+?\\d{9,14}$")`                                                                                           | Insira um número de telefone que tenha entre 9 e 14 dígitos numéricos. O sinal de + é opcional.                                                                          |
| `address`         | `@Size(min = 10, max = 250)`                                                                                                     | O endereço deve ter entre 10 e 250 caracteres.                                                                                                                           |

| Campo             | Validação                                                          | Obrigatório | Mensagem de Erro                                                                                                                            |
|:------------------|:-------------------------------------------------------------------|:------------|:--------------------------------------------------------------------------------------------------------------------------------------------|
| `name`            |                                                                    | Sim         | O nome deve ter entre 3 e 250 caracteres.                                                                                                   |
| `email`           | `^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$`                    | Sim         | Email deve ser válido.                                                                                                                      |
| `password`        | `^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$` | Sim         | A senha deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial. |
| `confirmPassword` |                                                                    | Sim         | Confirmar senha deve ser igual ao valor inserido no campo senha                                                                             |
| `cpf`             | `\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}`                                  | Sim         | Número de documento, do tipo CPF, deve ser válido.                                                                                          |
| `phoneNumber`     | `^\\+?\\d{9,14}$`                                                  | Não         | O número de telefone deve ter entre 9 e 14 dígitos numéricos. O sinal de + é opcional.                                                      |
| `address`         |                                                                    | Não         | O endereço deve ter entre 10 e 250 caracteres.                                                                                              |

### Possíveis HTTP Status

#### Sucesso

| Ação              | HTTP Status      | Descrição                       |
|-------------------|------------------|---------------------------------|
| Cadastrar usuário | `201 Created`    | Usuário criado com sucesso.     |
| Consultar usuário | `200 OK`         | Usuário consultado com sucesso. |
| Listar usuário    | `200 OK`         | Usuário listado com sucesso.    |
| Atualizar usuário | `200 OK`         | Usuário atualizado com sucesso. |
| Desativar usuário | `204 No Content` | Usuário desativado com sucesso. |

#### Erro

| Cenário                              | HTTP Status                 | Descrição                                                                  |
|--------------------------------------|-----------------------------|----------------------------------------------------------------------------|
| Validação de campos (qualquer campo) | `400 Bad Request`           | Algum campo não passou na validação, conforme mensagens de erro.           |
| E-mail duplicado                     | `409 Conflict`              | O e-mail informado já está em uso por outro usuário.                       |
| CPF duplicado                        | `409 Conflict`              | O CPF informado já está em uso por outro usuário.                          |
| Usuário não encontrado               | `404 Not Found`             | Usuário não encontrado para operações de consulta, atualização ou deleção. |
| Erro interno do servidor             | `500 Internal Server Error` | Algum erro inesperado ocorreu no servidor.                                 |

---

| Campo   | Mensagem de Erro / Exemplo / Regex                     |
|:--------|:-------------------------------------------------------|
| `name`  | Por favor, insira o seu nome.                          |
| `name`  | O nome deve ter entre 3 e 250 caracteres.              |
| `email` | Por favor, informe um e-mail válido.                   |
| `email` | Exemplo: seuemail@dominio.com.                         |
| `email` | Regex: `^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$` |