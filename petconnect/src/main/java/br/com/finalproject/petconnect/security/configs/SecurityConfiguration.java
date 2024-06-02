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
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;


@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
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
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
                        config.setMaxAge(3600L);
                        return config;
                    });
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> {
                    log.info("Configurando autorizações de requisição");
                    requests.requestMatchers("/api/v1", "/api/v1/**", "/auth/**",
                                    "/auth/login", "/auth/signup").permitAll()
                            .requestMatchers("/users", "/users/**").authenticated()
                            .requestMatchers("/pets", "/pets/**").authenticated()
                            .requestMatchers("/appointments", "/appointments/**").authenticated();
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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        log.info("Configurando SecurityFilterChain");
//
//        try {
//            http
//                    .sessionManagement(session -> {
//                        log.info("Configurando gerenciamento de sessão como STATELESS");
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                    })
//                    .cors(cors -> {
//                        log.info("Configurando CORS");
//                        cors.configurationSource(corsConfigurationSource());
//                    })
//                    .csrf(csrf -> {
//                        log.info("Desabilitando CSRF para simplificar");
//                        csrf.disable();
//                    })
//                    //.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                    .authorizeHttpRequests(requests -> {
//                        log.info("Configurando autorizações de requisição");
//                        requests
//                                .requestMatchers("/api/v1", "/api/v1/**", "/error").permitAll()
//                                .requestMatchers("/api-docs", "/swagger-ui.html").permitAll()
//                                .requestMatchers("/actuator", "/actuator/**").permitAll()
//                                .requestMatchers("/auth/login", "/auth/signup", "/auth/reset-password").permitAll()
//                                .requestMatchers("/auth/login").anonymous()
//                                .anyRequest().authenticated();
//                    })
//                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            log.info("SecurityFilterChain configurado com sucesso");
//        } catch (Exception e) {
//            log.error("Erro ao configurar o SecurityFilterChain", e);
//            throw e;
//        }
//
//        return http.build();
//    }
//
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        try {
//            final var config = getCorsConfiguration();
//            var source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", config); // Aplicar configuração a todos os endpoints
//            log.info("Configuração de CORS aplicada com sucesso");
//            return source;
//        } catch (Exception e) {
//            log.error("Erro ao configurar CORS", e);
//            throw e;
//        }
//    }
//
//    private static CorsConfiguration getCorsConfiguration() {
//        var config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("https://localhost:4200", "https://localhost:9090", "*"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
//        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//        config.setAllowCredentials(true);
//        return config;
//    }

}
