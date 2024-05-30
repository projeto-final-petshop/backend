# Dashboard

## Agendamento `/appointments`

* ID do Pet
* ID do Usuário
* Tipo de Serviço: banho, tosa, banho e tosa, consulta veterinária
* Tipo de Pet: gato, cachorro, outros
* Status do Agendamento: agendado, cancelado, concluído, pendente, confirmado.
* Data: dd/MM/yyyy
* Hora: HH:mm

### Mapeamento das ações permitidas para cada recurso

| Recurso            | Ação                             | Permissão | Status Code de Sucesso | Status Code de Erro                                   |
|:-------------------|:---------------------------------|:----------|:-----------------------|:------------------------------------------------------|
| Agenda             | Visualizar todos os agendamentos | ADMIN     | 200 OK                 | 403 Forbidden                                         |
| Consultas/Serviços | Cadastrar                        | ADMIN     | 201 Created            | 403 Forbidden <br> 400 Bad Request                    |
| Consultas/Serviços | Atualizar                        | ADMIN     | 200 OK                 | 403 Forbidden <br> 400 Bad Request <br> 404 Not Found |
| Consultas/Serviços | Cancelar                         | ADMIN     | 200 OK                 | 403 Forbidden <br> 404 Not Found                      |
| Consultas/Serviços | Reagendar                        | ADMIN     | 200 OK                 | 403 Forbidden <br> 400 Bad Request <br> 404 Not Found |

Dono do Pet (USER) poderá se cadastrar, autenticar no sistema.
Dono do Pet (USER) poderá atualiazar seus dados, visualizar seu perfil e excluir sua conta no sistema.
Dono do Pet (USER) poderá cadatrar, atualizar, visualizar detalhes, listar e excluir Pet.
Dono do Pet (USER) poderá visualizar os agendamentos de serviços e consultas relacionados aos seus pets cadastrados.
Dono do Pet (USER) poderá filtrar agendamento por data e status dos seus pets.
Dono do PetShop (ADMIN) poderá visualizar todos os agendamentos, filtrar por status e data.
Dono do PetShop (ADMIN) poderá visualizar e atualizar dados do Pet.
Dono do PetShop (ADMIN) poderá listar todos os pets cadastrados e ver detalhes de cada um.
Dono do PetShop (ADMIN) poderá agendar consulta ou serviços para um Pet já cadastro que tenha um dono (USER).
Dono do PetShop (ADMIN) poderá cadastrar funcionário (EMPLOYEE) e veterinário (VETERINARIAN).
Funcionário (EMPLOYEE) poderá visualizar e listar Pets cadastrados.
Funcionário (EMPLOYEE) poderá visualizar e filtrar informações na agenda de serviços.
Funcionário (EMPLOYEE) poderá atualizar o status do agendamento.
Veterinário poderá visualizar a agenda de consultas.
Veterinário poderá atualizar o status do agendamento
Veterinário poderá incluir no sistema (perfil do Pet) informações veterinárias.