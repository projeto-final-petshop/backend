# DataSourceProperties

A classe `DataSourceProperties` no Spring Boot é uma classe de configuração que ajuda a configurar um datasource (fonte
de dados) para a aplicação. Ela fornece uma maneira prática de definir e inicializar as propriedades necessárias para
conectar-se a um banco de dados.

## Explicação das Propriedades

* `url`: A URL JDBC para o banco de dados MySQL, no formato `jdbc:mysql://<host>:<port>/<database>?<options>`. No
  exemplo, `localhost` é o `host`, `3306` é a porta padrão do `MySQL`, e `petconnect` é o nome do banco de dados. As
  opções `serverTimezone=UTC` e `useSSL=false` são usadas para definir o fuso horário do servidor e desativar o SSL,
  respectivamente.
* `username`: O nome de usuário para se conectar ao banco de dados.
* `password`: A senha para se conectar ao banco de dados.
* `driver-class-name`: O nome da classe do driver JDBC para MySQL.
* `ddl-auto`: Configura o comportamento de geração de esquema do Hibernate. O valor update atualiza o esquema do banco
  de dados conforme necessário.
* `show-sql`: Mostra as instruções SQL no console.
* `format_sql`: Formata as instruções SQL para melhor legibilidade.

### Propriedade `url`

A URL de conexão JDBC para o MySQL pode incluir várias propriedades adicionais para configurar diversos aspectos da
conexão com o banco de dados. Aqui estão algumas das propriedades mais comuns e úteis que você pode adicionar à sua URL:

* `serverTimezone`: Define o fuso horário do servidor: `serverTimezone=UTC`
* `useSSL`: Habilita ou desabilita o uso de SSL.
    * `useSSL=false`. Ao desabilitar a criptografia SSL, pode não ser seguro em ambientes de produção.
* `autoReconnect`: Tenta reconectar automaticamente ao banco de dados se a conexão for perdida.
    * `autoReconnect=true`. Ao habilitar pode afetar o desempenho da aplicação, pois poderá introduzir um pequeno atraso
      na tentativa de reconexão.
* `useUnicode=true`: Habilita o uso de caracteres Unicode.
* `characterEncoding=UTF-8`: Define a codificação de caracteres a ser usada.
* `allowPublicKeyRetrieval=true`: Necessário se você estiver usando autenticação baseada em chave pública.
* `zeroDateTimeBehavior=CONVERT_TO_NULL`: Define como o driver deve tratar valores de data e hora nulos.
* `useLegacyDatetimeCode=false`: Especifica se deve usar a manipulação de data/hora legada do MySQL.
* `createDatabaseIfNotExist=true`: Cria o banco de dados se ele não existir.
* `requireSSL=true`: Exige que a conexão use SSL.
* `verifyServerCertificate=true`: Verifica o certificado do servidor quando SSL está habilitado.
* `useLegacyDatetimeCode=false`: Especifica se deve usar a manipulação de data/hora legada do MySQL.
* `cachePrepStmts=true`: Habilita o cache de instruções preparadas.
* `prepStmtCacheSize=250`: Define o tamanho do cache para instruções preparadas.
* `prepStmtCacheSqlLimit=2048`: Define o tamanho máximo das instruções SQL que podem ser cacheadas.
* `useServerPrepStmts=true`: Usa instruções preparadas do servidor.
* `rewriteBatchedStatements=true`: Melhora o desempenho de lotes de inserção.

### Considerações

* **Segurança**: Propriedades como `useSSL` e `verifyServerCertificate` são importantes para garantir a segurança das
  conexões, especialmente em ambientes de produção.
* **Desempenho**: Propriedades como `cachePrepStmts`, `prepStmtCacheSize`, e `rewriteBatchedStatements` podem melhorar
  significativamente o desempenho da aplicação.
* **Confiabilidade**: Propriedades como `autoReconnect` e `createDatabaseIfNotExist` ajudam a melhorar a confiabilidade
  e a resiliência da aplicação.

### Exemplo Completo

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/petconnect?createDatabaseIfNotExist=true&requireSSL=true&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

```