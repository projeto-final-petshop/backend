package br.com.project.petconnect.core.security.config;

import br.com.project.petconnect.app.user.domain.entities.Authority;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, password,
                        getGrantedAuthorities(user.getAuthorities()));
            }
            throw new BadCredentialsException("Senha inválida!");
        }
        throw new BadCredentialsException("Nenhum usuário registrado com esses dados!");
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
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
