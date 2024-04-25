package br.com.projetofinal.petconnet.core.security;

import br.com.projetofinal.petconnet.core.security.jwt.JwtAuthenticationFilter;
import br.com.projetofinal.petconnet.core.security.jwt.JwtProvider;
import br.com.projetofinal.petconnet.core.security.service.CustomUserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * Classe de configuração de segurança para a API.
 * <p>
 * Essa classe configura a segurança da API utilizando Spring Security. Ela define políticas de autenticação,
 * autorização, CORS e CSRF.
 */
@Configuration
@AllArgsConstructor
public class ApiSecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImpl userDetailsService;

    /**
     * Define a cadeia de filtros de segurança padrão.
     * <p>
     * Esse método configura diversas opções de segurança, incluindo:
     * <p>
     * * Gerenciamento de sessão: define a política de criação de sessão como STATELESS, indicando que a aplicação não
     * utilizará sessões.
     * <p>
     * * CORS: permite requisições de origens diferentes (localhost:4200 por padrão) com todos os métodos, credenciais,
     * cabeçalhos e com tempo de vida de 1 hora.
     * <p>
     * * CSRF: habilita proteção CSRF com token armazenado em cookie (não somente HTTP Only), ignorando requisições para
     * rotas específicas (/api/v1/**, /users/register).
     * <p>
     * * Filtros: adiciona o filtro {@link CsrfCookieFilter} após o {@link BasicAuthenticationFilter}.
     * <p>
     * * Autorização: define que as rotas "/actuator/**", "/address/**", "/pets/**", "/users/list", "/users/{userId}/**"
     * e "/users/{username}**" requerem autenticação, enquanto as rotas "/api/v1/**", "/users/register" e "/login"
     * permitem acesso sem autenticação.
     * <p>
     * * Login via formulário: define a rota de login como "/login" e permite acesso sem autenticação.
     * <p>
     * * Autenticação básica: habilita autenticação básica com configurações padrão.
     *
     * @param http
     *         Objeto HttpSecurity utilizado para configurar a segurança.
     *
     * @return SecurityFilterChain cadeia de filtros de segurança construída.
     *
     * @throws Exception
     *         caso ocorra alguma exceção durante a configuração.
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    var config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(3600L);
                    return config;
                }))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/v1/**", "/users/register", "/auth/login", "/login")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), ExceptionTranslationFilter.class)
                .exceptionHandling(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/actuator/**", "/address/**", "/pets/**",
                                "/users/list", "/users/{userId}/**", "/users/{username}").authenticated()
                        .requestMatchers("/api/v1/**", "/users/register", "/login", "/auth/login").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

    /**
     * Define o codificador de senha padrão.
     * <p>
     * Esse método retorna um codificador de senha do tipo BCryptPasswordEncoder, utilizado para armazenar senhas com
     * segurança.
     *
     * @return {@link PasswordEncoder} codificador de senha.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

}