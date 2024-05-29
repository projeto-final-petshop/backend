# Dashboard

## Agendamento

* Identificador do serviço/agendamento (ID)
* Tipo de serviço (banho e tosa, consulta veterinária, etc.)
* Data e hora do agendamento
* Cliente (nome, email, telefone, etc.)
* Informações adicionais (como tipo de animal, observações, etc.)

Appointment `/appointments`

- id
- serviceType
- dateTime
- client
- additionalInfo

ja fiz a tela inicial pra criar um novo agendamento também, onde o usuário vai escolher o pet, o serviço, e o horário de inicio do atendimento. apos isso, fiz um calculo automático pro horário de termino

(deixei a consulta de uma hora pra banho, uma hora pra tosa, duas horas pra banho e tosa, e meia hora pro veterinário)

aí o horário de termino é indisponível pra alteração, so mostra a visualização. e vou te mandar isso pra criar um novo agendamento, ou seja:
(token no headers)
-id do pet escolhido
-dia
-serviço
-horário de inicio
-horário de termino


Pet: selecionar o tipo de pet (gato, cachorro, outros)
Serviços: banho, tosa, banho e tosa, consulta veterinária
Horário de início
Horário de término