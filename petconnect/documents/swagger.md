# Swagger - OpenAPI

### Dependências

```xml

<dependencies>
    <!-- SpringDoc OpenAPI Starter WebMVC UI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
</dependencies>

```

http://localhost:8888/api/v1/v3/api-docs

### URLs

* Caminho padrão: `http://localhost:8080/v3/api-docs`
* Configuração para alterar o caminho padrão para `http://localhost:8080/api-docs`

```yaml
springdoc:
  api-docs:
    path: /api-docs
```

* Definições do OpenAPI estão no formato `json` por padrão. Para o formato `yaml`, podemos obter as definições
  em: `http://localhost:8080/swagger-ui/index.html`

### Integração com UI Swagger

* Dependência `springdoc-openapi` já inclui o Swagger UI
* Link: `http://localhost:8080/swagger-ui/index.html`

* Personalizando o caminho da documentação

```yaml
springdoc:
  api-docs:
    path: /swagger-ui-custom.html
```

* Assim, a documentação da API estará disponível no link: `http://localhost:8080/swagger-ui-custom.html`

* Classificar os caminhos da API de acordo com seus métodos HTTP:

```yaml
springdoc:
  api-docs:
    operations-sorter: method
```

### Usando o plug-in Springdoc-openapi Maven

A biblioteca springdoc-openapi fornece um plugin Maven, springdoc-openapi-maven-plugin , que gera descrições OpenAPI nos
formatos JSON e YAML.

O plugin springdoc-openapi-maven-plugin funciona com o plugin spring-boot-maven . Maven executa o plugin openapi durante
a fase de teste de integração .

Configuração:

```xml

<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>3.1.5</version>
        <executions>
            <execution>
                <id>pre-integration-test</id>
                <goals>
                    <goal>start</goal>
                </goals>
            </execution>
            <execution>
                <id>post-integration-test</id>
                <goals>
                    <goal>stop</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    <plugin>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-maven-plugin</artifactId>
        <version>1.4</version>
        <executions>
            <execution>
                <phase>integration-test</phase>
                <goals>
                    <goal>generate</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

Plugin para usar valores personalizados:

```xml

<plugins>
    <plugin>
        <executions>
            <!-- ......... -->
        </executions>
        <configuration>
            <apiDocsUrl>http://localhost:8080/v3/api-docs</apiDocsUrl>
            <outputFileName>openapi.json</outputFileName>
            <outputDir>${project.build.directory}</outputDir>
        </configuration>
    </plugin>
</plugins>
```

* `apiDocsUrl` — URL onde os documentos podem ser acessados emm formato JSON, com
  padrão http://localhost:8080/v3/api-docs
* `outputFileName` — Nome do arquivo onde as definições estão armazenadas; o padrão é `openapi.json`
* `outputDir` — Caminho absoluto do diretório onde os documentos estão armazenados; por padrão,
  é `${project.build.directory}`