package br.com.project.petconnect.core.security.config;

import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Provedor de autenticação personalizado para a aplicação PetConnect.
 * <p>
 * Esta classe implementa a interface {@link AuthenticationProvider} e é responsável por validar as credenciais (e-mail
 * e senha) informadas pelo usuário durante a tentativa de autenticação.
 *
 * @author juliane.maran
 */
@Component
@RequiredArgsConstructor
public class PetConnectAuthenticationProvider implements AuthenticationProvider {

    /**
     * Repositório de dados para acesso à entidade {@link UserEntity}.
     */
    private final UserRepository userRepository;

    /**
     * Bean de codificação de senha utilizado para comparar a senha informada com a senha armazenada (criptografada).
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Realiza a autenticação do usuário.
     * <p>
     * Este método recebe um objeto {@link Authentication} contendo o nome de usuário e senha informados.
     * <p>
     * Ele busca o usuário pelo e-mail no repositório, compara a senha informada com a senha criptografada armazenada, e
     * caso a autenticação seja válida, retorna um novo objeto {@link UsernamePasswordAuthenticationToken} contendo as
     * informações do usuário autenticado e suas autoridades.
     *
     * @param authentication
     *         Objeto {@link Authentication} contendo as credenciais do usuário (e-mail e senha).
     *
     * @return Objeto {@link Authentication} contendo as informações do usuário autenticado e suas autoridades.
     *
     * @throws AuthenticationException
     *         Exceção caso a autenticação falhe.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválido!"));

        validatePassword(password, user.getPassword());

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Senha inválida!");
        }
    }

    /**
     * Verifica se o provedor suporta o tipo de autenticação fornecido.
     * <p>
     * Este método verifica se a classe de autenticação informada é compatível com
     * {@link UsernamePasswordAuthenticationToken}.
     *
     * @param authentication
     *         A classe de autenticação a ser verificada.
     *
     * @return true se o provedor suporta a classe de autenticação, false caso contrário.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
