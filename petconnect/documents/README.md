## PetConnect

### Estrutura do Cadastro de Animais:

Para garantir um cadastro completo e eficiente, vamos considerar os seguintes campos:

* **ID**: Um identificador único para cada animal, geralmente um número inteiro autogerenciado.
* **Nome** (name): Nome do animal, um campo de texto obrigatório.
* **Espécie** (Species): Tipo de animal, como "Cachorro", "Gato", "Aves", etc., com um campo de texto ou lista predefinida.
* **Raça** (Breed): Raça específica do animal, um campo de texto ou lista predefinida com raças populares.
* **Sexo** (Sex): Sexo do animal, masculino, feminino ou indefinido, com botões de seleção ou lista predefinida.
* **Idade**: Idade do animal em anos, meses ou semanas, com campos de entrada numéricos.
* **Porte**: Porte do animal, pequeno, médio ou grande, com botões de seleção ou lista predefinida.
* **Castrado**: Se o animal é castrado ou não, com botões de seleção (sim/não).
* **Vacinação**: Se o animal está em dia com as vacinas, com botões de seleção (sim/não).
* **Foto**: Uma foto do animal, armazenada como um arquivo de imagem no sistema de arquivos ou em um banco de dados
  binário.
* **Descrição**: Uma descrição opcional do animal, com um campo de texto livre.
* **PetShop**: Se o animal está cadastrado em um PetShop, com um campo de texto para buscar ou selecionar o PetShop.
* **Tutor**: Se o animal possui um tutor, com um campo de texto para buscar ou selecionar o tutor.

### Tecnologias Utilizadas:

* **Spring Data JPA**: Para mapear as entidades do seu modelo de dados para as tabelas do banco de dados MySQL e
  gerenciar o acesso aos dados de forma eficiente.
* **Entidades**: Criação de classes Java para representar cada entidade do seu modelo de dados, como Animal, PetShop e
  Tutor.
* **Repositórios**: Criação de interfaces de repositório que definem as operações CRUD (Criar, Ler, Atualizar e Excluir)
  para cada entidade, utilizando anotações do Spring Data JPA.
* **Serviços**: Criação de classes de serviço para encapsular a lógica de negócio relacionada ao cadastro de animais,
  como salvar, buscar, atualizar e excluir animais.
* **Controladores**: Criação de classes de controlador para gerenciar as solicitações HTTP relacionadas ao cadastro de
  animais, como criar, ler, atualizar e excluir animais.

### Armazenamento de Imagens:

Existem duas abordagens comuns para armazenar fotos de animais:

1. **Sistema de Arquivos**: A imagem é salva como um arquivo separado em uma pasta específica do projeto. Você precisa
   implementar lógica para salvar e recuperar o caminho do arquivo associado ao animal.
2. **Banco de Dados Binário**: A imagem é convertida em um array de bytes e armazenada diretamente em um campo LOB (
   Large Object) da tabela Animal no MySQL. Essa abordagem requer menos manutenção de arquivos, mas pode impactar o
   tamanho do banco de dados.

### Validação de Dados:

* Implemente validações nas classes de serviço ou controladores para garantir que os dados informados no cadastro do
  animal sejam válidos. Por exemplo, verifique se o nome está preenchido, se a idade é um valor positivo e se o sexo é
  um dos valores permitidos.
* Você pode utilizar anotações do Hibernate como @NotBlank e @Min para simplificar a validação.

### Relacionamentos entre Entidades:

* As entidades Animal, PetShop e Tutor possuem relacionamentos ManyToOne. Isso significa que um animal pode estar
  associado a um único PetShop e a um único Tutor (opcional).
* A anotação @ManyToOne define esse relacionamento nas classes Animal, PetShop e Tutor.
* Você precisará implementar lógica para buscar e associar o PetShop e/ou Tutor ao animal durante o cadastro.
* **Um Tutor pode ter vários Animais**: A entidade Tutor possui um relacionamento ManyToOne com a entidade Animal, o que
  significa que um tutor pode ter vários animais cadastrados em seu nome.
* **Um PetShop pode ter vários Animais**: A entidade PetShop também possui um relacionamento ManyToOne com a entidade
  Animal, indicando que um PetShop pode cuidar de vários animais.

### Entidade Tutor:

* **ID**: Identificador único para cada tutor (geralmente auto-incrementado).
* **Nome**: Nome completo do tutor.
* **CPF**: CPF do tutor (opcional, para fins de identificação e cadastro).
* **Telefone**: Número de telefone principal do tutor.
* **Celular**: Número de telefone celular do tutor (opcional).
* **Email**: Endereço de email do tutor.
* **Endereço**: Endereço residencial completo do tutor (logradouro, número, complemento, bairro, cidade, estado, CEP).

### Entidade PetShop:

* **ID**: Identificador único para cada PetShop (geralmente auto-incrementado).
* **Nome**: Nome fantasia do PetShop.
* **CNPJ**: CNPJ do PetShop.
* **Telefone**: Número de telefone principal do PetShop.
* **Celular**: Número de telefone celular do PetShop (opcional).
* **Email**: Endereço de email do PetShop.
* **Endereço**: Endereço completo do PetShop (logradouro, número, complemento, bairro, cidade, estado, CEP).
* **Serviços**: Uma lista de serviços oferecidos pelo PetShop (banho, tosa, adestramento, etc.).
* **Horário de Funcionamento**: Horário de funcionamento do PetShop (dias da semana e horários).

