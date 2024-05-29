# CORS (Cross-Origin Resource Sharing)

Configurar CORS (Cross-Origin Resource Sharing) no Spring Security é fundamental para permitir que recursos da sua
aplicação sejam acessados por domínios diferentes do servidor da aplicação. Vamos explicar como configurar o CORS
utilizando o Spring Security e a classe `CorsConfiguration`.

### O que é CORS?

CORS é um mecanismo que permite que recursos restringidos em uma página web sejam requisitados por outro domínio fora do
domínio do qual o recurso se originou. Ele envolve cabeçalhos HTTP que permitem que um servidor especifique quaisquer
origens (domínios) permitidas a acessar seus recursos.

### Configuração do CORS no Spring Security

A configuração do CORS no Spring Security é feita utilizando a classe `CorsConfiguration` e `CorsConfigurationSource`
para definir as regras CORS, e `CorsFilter` para aplicar essas regras.

**Passo a Passo para Configurar CORS**

1. Defina uma Configuração CORS

   Você precisa criar uma instância de CorsConfiguration e definir as configurações necessárias, como origens
   permitidas, métodos permitidos, cabeçalhos permitidos, etc.

   ```java
   import org.springframework.web.cors.CorsConfiguration;
   import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
   import org.springframework.web.filter.CorsFilter;
   
   import java.util.Arrays;
   
   public CorsFilter corsFilter() {
   CorsConfiguration corsConfiguration = new CorsConfiguration();
   // Call to 'asList()' with only one argument
   corsConfiguration.setAllowedOrigins(Arrays.asList("https://example.com")); // Configurações de origem permitidas
   corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Métodos HTTP permitidos
   corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Cabeçalhos permitidos
   corsConfiguration.setAllowCredentials(true); // Permitir credenciais como cookies
   
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", corsConfiguration); // Aplicar configuração a todos os endpoints
   
       return new CorsFilter(source);
   }
   ```

2. Registrar o CorsFilter no Spring Security

   Após definir a configuração CORS, você deve registrar o CorsFilter no Spring Security.

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
   import org.springframework.security.web.SecurityFilterChain;
   
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig {
   
       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http
                // 'cors()' is deprecated since version 6.1 and marked for removal
               .cors().and() // Ativa o CORS
                // 'csrf()' is deprecated since version 6.1 and marked for removal
               .csrf().disable() // Desabilita CSRF para simplificar
                // 'authorizeRequests()' is deprecated
               .authorizeRequests()
                   .anyRequest().authenticated();
   
           return http.build();
       }
   
       @Bean
       public CorsFilter corsFilter() {
           return new CorsFilter(corsConfigurationSource());
       }
   
       @Bean
       public UrlBasedCorsConfigurationSource corsConfigurationSource() {
           CorsConfiguration corsConfiguration = new CorsConfiguration();
           corsConfiguration.setAllowedOrigins(Arrays.asList("https://example.com"));
           corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
           corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
           corsConfiguration.setAllowCredentials(true);
   
           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
           source.registerCorsConfiguration("/**", corsConfiguration);
           return source;
       }
   }
   ```

### Explicação dos Parâmetros

* `setAllowedOrigins`: Define quais origens são permitidas para acessar os recursos da sua aplicação.
* `setAllowedMethods`: Especifica quais métodos HTTP são permitidos (GET, POST, etc.).
* `setAllowedHeaders`: Lista os cabeçalhos permitidos nas requisições.
* `setAllowCredentials`: Permite o uso de credenciais (como cookies e autenticação HTTP) nas requisições.

### Aplicar Configurações Padrão

Você pode utilizar o método `applyPermitDefaultValues()` para aplicar configurações padrão, que permitem todas as
requisições de origem cruzada para métodos GET, HEAD e POST.

`CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();`

Essa configuração simplifica o processo, mas é importante ajustar as permissões de acordo com as necessidades de
segurança da sua aplicação.




