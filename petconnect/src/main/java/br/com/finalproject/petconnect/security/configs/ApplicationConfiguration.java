package br.com.finalproject.petconnect.security.configs;

import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@AllArgsConstructor
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    /**
     * Define como recuperar o usuário usando o {@link UserRepository} injetado
     *
     * @return o usuário
     */
    @Bean
    UserDetailsService userDetailsService() {
        log.info("Configurando UserDetailsService");
        return username -> {
            log.info("Buscando usuário pelo email: {}", username);
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> {
                        log.warn("Usuário não encontrado: {}", username);
                        return new UsernameNotFoundException("User not found");
                    });
        };
    }

    /**
     * Cria uma instância do {@link BCryptPasswordEncoder} usado para codificar a senha simples do usuário
     *
     * @return a senha codificada
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        log.info("Configurando BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    /**
     * Define a nova estratégia para realizar a autenticação
     *
     * @param config
     *         define a estratégia
     *
     * @return o usuário autenticado
     *
     * @throws Exception
     *         exceção lançada em caso de falha
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("Configurando AuthenticationManager");
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        log.info("Configurando AuthenticationProvider");
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
