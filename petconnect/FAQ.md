### FAQ: Cadastrar Usuário e Validação de Senhas

#### Informações Gerais

1. **Quais são os requisitos para o nome?**

- **Resposta:** O nome deve ter entre 3 e 250 caracteres.

2. **Como devo inserir meu e-mail?**

- **Resposta:** Por favor, insira um endereço de e-mail válido. Certifique-se de que está no formato correto (
  exemplo@dominio.com).

3. **Como deve ser inserido o CPF?**

- **Resposta:** Por favor, insira um CPF válido no formato `XXX.XXX.XXX-XX`. A regex utilizada para validar o CPF
  é `\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}`.

4. **Quais são os requisitos para o número de telefone?**

- **Resposta:** O número de telefone deve ter entre 9 e 14 dígitos numéricos. O sinal de + é opcional. A regex utilizada
  para validar o telefone é `^\\+?\\d{9,14}$`.

5. **Quais são os requisitos para o endereço?**

- **Resposta:** O endereço deve ter entre 10 e 250 caracteres.

6. **Por que minha conta está inativa?**

- **Resposta:** Se você recebeu a mensagem "Usuário inativo", significa que sua conta não está ativada. Entre em contato
  com o suporte para ativação.

7. **Por que estou vendo uma mensagem de permissão negada?**

- **Resposta:** A mensagem "Usuário não possui permissão para acessar a página" significa que sua conta não tem os
  privilégios necessários para acessar a página. Contate o administrador para mais detalhes.

#### Senha

8. **Quais são os requisitos para a senha?**

- **Resposta:** A senha deve ter pelo menos 8 caracteres, incluindo pelo menos:
    - Uma letra maiúscula.
    - Uma letra minúscula.
    - Um número.
    - Um caractere especial (ex.: !@#$%^&*()-+).

9. **Recebi uma mensagem de que minha senha não atende aos requisitos. O que devo fazer?**

- **Resposta:** Verifique se sua senha:
    - Tem pelo menos 8 caracteres.
    - Inclui pelo menos uma letra maiúscula.
    - Inclui pelo menos uma letra minúscula.
    - Inclui pelo menos um número.
    - Inclui pelo menos um caractere especial.

10. **Qual é a mensagem de erro geral para o campo Senha?**

- **Resposta:** "O valor digitado no campo Senha não está correto. Digite a senha correta."

11. **O que acontece se eu inserir menos de 8 caracteres na senha?**

- **Resposta:** "A senha não contém o número mínimo obrigatório de caracteres."

12. **O que acontece se a senha não atender a alguma regra específica?**

- **Resposta:** "A senha não atende aos requisitos das regras de senhas."

#### Nova Senha (newPassword)

13. **O que fazer se a nova senha for igual a uma senha usada anteriormente?**

- **Resposta:** "A nova senha não pode ser igual a quaisquer senhas utilizadas anteriormente."

14. **E se o campo Nova Senha estiver vazio?**

- **Resposta:** "O valor do campo Nova Senha está faltando."

#### Confirmar Senha

15. **Por que as senhas precisam coincidir?**

- **Resposta:** As senhas precisam coincidir para garantir que você digitou corretamente sua senha desejada duas vezes,
  evitando erros de digitação.

16. **O que fazer se o campo Confirmar Senha estiver vazio?**

- **Resposta:**
    - Para senha nova: "O valor do campo Confirmar Senha está faltando. Digite o mesmo valor inserido no campo Nova
      Senha."
    - Para senha antiga: "O valor do campo Confirmar Senha está faltando. Digite o mesmo valor inserido no campo Senha."

#### Senha Atual / Senha Antiga

17. **O que fazer se o campo Senha Antiga estiver vazio?**

- **Resposta:** "O valor para o campo Senha Antiga está ausente."

18. **E se a senha antiga digitada estiver incorreta?**

- **Resposta:** "O valor digitado no campo Senha Antiga não está correto. Digite a senha correta."

#### Tentativa de Login com Conta Inativa

19. **Por que não consigo fazer login?**

- **Resposta:** "Não existe contas para esta pessoa. As contas podem estar inativas ou com acesso negado." Verifique se
  a conta está ativa ou contate o suporte para mais informações.