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

---

## GET /search

### Esrutura geral do cURL

```
curl -X GET "http://localhost:8080/users?parametros_de_busca" -H "accept: application/json"
```

Exemplos de cURL

1. Buscar usuários ativos, com nome parcial "John", email "john@example.com", CPF "12345678900", na primeira página (
   padrão) com 10 resultados por página, ordenados por nome ascendente.

   ```
   curl -X GET "http://localhost:8080/users?name=John&email=john@example.com&cpf=12345678900&active=true&page=0&size=10&sort=name,asc" -H "accept: application/json"
   ```

2. Buscar todos os usuários inativos, sem especificar nome, email ou CPF, na segunda página (página 1) com 5 resultados
   por página, ordenados por data de criação descendente.

   ```
   curl -X GET "http://localhost:8080/users?active=false&page=1&size=5&sort=createdAt,desc" -H "accept: application/json"
   ```

3. Buscar usuários sem filtros, apenas paginando e ordenando por email ascendente.

   ```
   curl -X GET "http://localhost:8080/users?page=0&size=20&sort=email,asc" -H "accept: application/json"
   ```

### Explicação do parâmetros

* `name`: filtro pelo nome (pode ser parcial)
* `email`: filtro pelo email
* `cpf`: filtro pelo CPF
* `active`: filtro pelo status ativo/inativo (`true` ou `false`)
* `page`: número de página (a contagem inicia em 0).
* `size`: número de registros por página.
* `sort`: campo e direção de ordenação
    * Exemplo: `name,asc` para ordenar nome ascendente

### Endpoints de Exemplo

```java

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<UserResponse> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.searchUsers(name, email, cpf, active, pageable);
    }
}

```

   