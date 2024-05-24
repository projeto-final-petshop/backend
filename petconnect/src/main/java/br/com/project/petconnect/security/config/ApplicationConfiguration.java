package br.com.project.petconnect.security.config;

import br.com.project.petconnect.user.repository.UserRepository;
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
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Configura o serviço para recuperar detalhes do usuário para autenticação.
     * <p>
     * Esse método cria um `UserDetailsService` que utiliza o `UserRepository` injetado para buscar o usuário por
     * e-mail. Se o usuário não for encontrado, uma exceção `UsernameNotFoundException` é lançada.
     *
     * @return Um objeto `UserDetailsService` implementado para recuperar detalhes do usuário.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    /**
     * Configura o codificador de senha para a aplicação.
     * <p>
     * Esse método cria um bean do tipo `BCryptPasswordEncoder`, que é utilizado para codificar com segurança as senhas
     * dos usuários antes de serem armazenadas no banco de dados.
     *
     * @return Um bean do tipo `BCryptPasswordEncoder` para codificação de senhas.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o gerenciador de autenticação.
     * <p>
     * Esse método recupera o gerenciador de autenticação a partir da configuração (`AuthenticationConfiguration`)
     * fornecida.
     *
     * @param config
     *         A configuração de autenticação.
     *
     * @return Um objeto `AuthenticationManager` utilizado para realizar a autenticação.
     *
     * @throws Exception
     *         Exceção caso haja algum problema ao recuperar o gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura o provedor de autenticação.
     * <p>
     * Esse método cria um `DaoAuthenticationProvider` e configura o `UserDetailsService` e o `BCryptPasswordEncoder`
     * para serem utilizados durante a autenticação.
     *
     * @return Um objeto `AuthenticationProvider` utilizado para realizar a autenticação.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
