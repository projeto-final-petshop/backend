# Informações sobre o Projeto

* O objetivo é utilizar o token gerado durante o login para vincular os dados do pet ao usuário que está realizando a
  operação.

1. **Login do Usuário e Geração de Token**
   Quando o usuário realizar o login, o sistema deve autenticar as credenciais fornecidas e gerar um token JWT (JSON Web
   Token) que contenha as informações necessárias para identificar o usuário. Esse token será então retornado ao cliente
   para uso subsequente em operações que requerem autenticação.
2. **Armazenamento do Token pelo Cliente**
   O cliente (geralmente um aplicativo frontend ou uma aplicação cliente) deve armazenar o token de forma segura,
   geralmente em local de armazenamento seguro como localStorage ou sessionStorage no caso de aplicações web, ou no
   armazenamento seguro do dispositivo no caso de aplicativos móveis.
3. **Utilização do Token para Vincular Dados do Pet ao Usuário**
   Quando o cliente realizar a requisição para criar um novo pet, ele deve incluir o token JWT no cabeçalho de
   autorização da requisição. O servidor então irá extrair as informações do usuário do token JWT e vincular os dados do
   pet a esse usuário antes de salvar no banco de dados.
4. **Implementação no Backend**
    * Verificar o Token no Backend
        * No backend, você precisará implementar um mecanismo para verificar e decodificar o token JWT recebido no
          cabeçalho de autorização da requisição.
    * Extrair as Informações do Usuário do Token
        * Após a verificação do token, você pode extrair as informações do usuário, como o ID do usuário, do próprio
          token JWT.
    * Associar o Pet ao Usuário
        * Com as informações do usuário extraídas do token, você pode então associar o pet ao usuário antes de salvá-lo
          no banco de dados.





   