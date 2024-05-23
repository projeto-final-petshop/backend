package br.com.project.petconnect.core.security.config;

import br.com.project.petconnect.core.security.ExceptionHandlerFilter;
import br.com.project.petconnect.core.security.filter.CsrfCookieFilter;
import br.com.project.petconnect.core.security.filter.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * Classe de configuração de segurança da aplicação PetConnect.
 * <p>
 * Essa classe configura aspectos de segurança como autenticação, autorização, CORS e CSRF.
 *
 * @author juliane.maran
 */
@Configuration
@RequiredArgsConstructor
public class PetConnectSecurityConfig {

    /**
     * Configura a cadeia de segurança padrão da aplicação.
     *
     * @param http
     *         Objeto {@link HttpSecurity} utilizado para configurar a segurança.
     *
     * @return Objeto {@link SecurityFilterChain} contendo a cadeia de segurança configurada.
     *
     * @throws Exception
     *         Exceção caso haja erro durante a configuração da segurança.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        var authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .headers(AbstractHttpConfigurer::disable)
                // Define o gerenciamento de sessão como STATELESS (sem estado).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define a configuração de CORS para permitir requisições originadas de uma determinada origem.
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    var config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
//                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setMaxAge(3600L);
                    return config;
                }))
                // Define a configuração do CSRF (Cross-Site Request Forgery).
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/v1", "/api/v1/**", "/error", "/auth/token",
                                "/auth/details", "/users/register", "/users/**", "/users")
                        // Define o repositório de tokens CSRF utilizando cookies com HttpOnly desabilitado.
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                // Adiciona o filtro CsrfCookieFilter após o filtro BasicAuthenticationFilter.
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
//                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                // Define regras de autorização de acesso a rotas.
                .authorizeHttpRequests((requests) -> requests
                        // Rotas que precisam de autenticação para serem acessadas
                        .requestMatchers("/users/change-password/**").authenticated()
                        .requestMatchers("/pets", "/pets/**").authenticated()
                        .requestMatchers("/petshops", "/petshops/**").authenticated()
                        .requestMatchers("/schedules", "/schedules/**").authenticated()
                        // Rotas que estão permitidas sem autenticação
                        .requestMatchers("/api/v1", "/api/v1/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/token", "/auth/details").permitAll()
                        .requestMatchers("/users/register", "/users/**", "/users", "/authenticate").permitAll()
                )
                // Habilita formulário de login básico (ajuste de acordo com a necessidade).
                .formLogin(Customizer.withDefaults())
                // Habilita autenticação básica (ajuste de acordo com a necessidade).
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

    /**
     * Define o bean para codificação de senha utilizando o algoritmo BCrypt.
     *
     * @return Instância de {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
