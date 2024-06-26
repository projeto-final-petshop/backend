# Two-factor authentication (2FA)

A autenticação em dois fatores (2FA) é uma camada adicional de segurança que requer duas formas de autenticação para
verificar a identidade do usuário. No contexto do Spring Boot em Java, podemos implementar o 2FA usando Tokens JWT (Json
Web Tokens) e Spring Security.

## JWT (Json Web Tokens)

* O JWT é um padrão amplamente usado para segurança em APIs REST. Ele permite que o servidor gere um Token após a
  autenticação bem-sucedida do usuário.
* O Token JWT é então enviado para o cliente (frontend) e incluído no cabeçalho de solicitações subsequentes.
* O backend verifica a validação do token e autoriza ou rejeita as solicitações com base nele.

* Spring Authentication Manager: serve para autenticar o nome de usuário e senha. Se as credenciais forem válidas, crie
  um Token JWT usaando o `JWTTokentUtil` e forneça-o ao cliente.
* O cliente deve incluir o token no cabeçalho de autorização das solicitações futuras (no formato “Bearer TOKEN”).
* O back-end verifica a validade do token e autoriza as solicitações com base nas funções de usuário armazenadas no
  token.
 
