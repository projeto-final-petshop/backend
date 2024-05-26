package br.com.finalproject.petconnect.security.configs;

import br.com.finalproject.petconnect.security.filter.CsrfCookieFilter;
import br.com.finalproject.petconnect.security.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("Configurando SecurityFilterChain");

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                .sessionManagement(session -> {
                    log.info("Configurando gerenciamento de sessão como STATELESS");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .cors(corsCustomizer -> {
                    log.info("Configurando CORS");
                    corsCustomizer.configurationSource(request -> {
                        var config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(List.of("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
                        config.setMaxAge(3600L);
                        return config;
                    });
                })
                .csrf(csrf -> {
                    log.info("Configurando CSRF");
                    csrf.csrfTokenRequestHandler(requestHandler)
                            .ignoringRequestMatchers(
                                    "/api/v1", "/api/v1/**", "/auth/**", "/users/**", "/users",
                                    "/auth/login", "/auth/signup", "/users/update-password",
                                    "/users/reset-password", "/users/reset-password/confirm")
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

                })
                .authorizeHttpRequests(requests -> {
                    log.info("Configurando autorizações de requisição");
                    requests.requestMatchers(
                            "/api/v1", "/api/v1/**", "/auth/**", "/users/**", "/users",
                            "/auth/login", "/auth/signup", "/users/update-password",
                            "/users/reset-password", "/users/reset-password/confirm").permitAll();
//                            .anyRequest().authenticated();
                    // .requestMatchers("/users", "/users/**").authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        log.info("SecurityFilterChain configurado com sucesso");
        return http.build();

    }


}
