## Esqueci minha Senha

A senha criptografada não pode ser revertida, por isso é necessário gerar uma nova senha temporária e enviá-la ao
Usuário.

O usuário então pode usar essa senha temporária para acessar o sistema e redefinir sua senha.

1. `ForgotPasswordRequest` - Classe para receber o email do usuário.
2. `PasswordService` - Serviço para gerar a nova senha temporária e enviar o email.
3. `EmailService` - Serviço para enviar emails.
4. `PasswordResetToken` - Entidade para armazenar tokens de redefinição de senha.
5. `PasswordResetTokenRepository` - Repositório para gerenciar tokens de redefinição de senha.
6. `PasswordController` - Controlador para expor endpoints de redefinição de senha.
7. Exceções personalizadas para lidar com erros.

## Configuração das propriedades para envio de email

* `spring.mail.host`: define o host do servidor de e-mail a ser usado para enviar e-mails.
* `spring.mail.port`: define a porta do servidor de e-mail a ser usada para enviar e-mails.
* `spring.mail.username`: define o nome de usuário usado para autenticar com o servidor de e-mail.
* `spring.mail.password`: define a senha usada para autenticar com o servidor de e-mail.
* `spring.mail.properties.mail.transport.protocol`: é o protocolo de transporte usado para enviar e-mails (no caso,
  SMTP).
* `spring.mail.properties.mail.smtp.starttls.enable`: indica se a conexão SMTP deve usar o protocolo TLS.
* `spring.mail.properties.mail.smtp.starttls.required`: indica se a conexão SMTP deve requerer o protocolo TLS.
* `spring.mail.properties.mail.smtp.auth`: indica se a autenticação deve ser usada para se conectar ao servidor de
  e-mail.
* `spring.mail.properties.mail.smtp.from`: define o endereço de e-mail usado como remetente para e-mails enviados.
* `spring.mail.default-encoding`: define a codificação de caracteres usada para o texto do e-mail.
* `spring.mail.test-connection`: indica se uma conexão de teste com o servidor de e-mail deve ser feita durante a
  inicialização do aplicativo.
* `mail.from.name`: define o nome do remetente usado para e-mails enviados.
