package br.com.finalproject.petconnect.security.configs;

import br.com.finalproject.petconnect.security.filter.CsrfCookieFilter;
import br.com.finalproject.petconnect.security.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String ALLOWED_ORIGINS_URL = "http://localhost:4200";

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("Configurando SecurityFilterChain");

        http
                .sessionManagement(session -> {
                    log.info("Configurando gerenciamento de sessão como STATELESS");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .cors(corsCustomizer -> {
                    log.info("Configurando CORS");
                    corsCustomizer.configurationSource(request -> {
                        var source = new UrlBasedCorsConfigurationSource();
                        var config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList(ALLOWED_ORIGINS_URL));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE));
                        config.setAllowCredentials(true);
                        config.setMaxAge(3600L);
                        source.registerCorsConfiguration("/**", config);
                        return config;
                    });
                })
                // CSRF vem habilitado por padrão pelo Spring Security
                // para realizar testes locais deve ficar desabilitado
                // TODO: HABILITAR QUANDO TESTAR COM FRONTEND
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    log.info("Configurando autorizações de requisição");
                    requests
                            .requestMatchers("/api/v1", "/api/v1/**", "/error").permitAll()
                            .requestMatchers("/auth/login", "/auth/signup", "/auth/reset-password").permitAll()
                            // TODO: INCLUIR RODAS E PERMISSÕES
                            .anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        log.info("SecurityFilterChain configurado com sucesso");
        return http.build();
    }

}
