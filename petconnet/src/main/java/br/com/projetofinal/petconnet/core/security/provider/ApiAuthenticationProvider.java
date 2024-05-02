package br.com.projetofinal.petconnet.core.security.provider;

import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.security.model.Authority;
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
 * Provedor de autenticação para a API
 *
 * <p>Esta classe implementa a interface {@link AuthenticationProvider} do Spring Security. Ela é responsável por
 * autenticar usuários que tentam acessar a API por meio de credenciais de username e password.</p>
 * <p>
 * {@code @Component} Marca a classe como um componente do Spring para ser gerenciado pelo Spring Framework.
 * <p>
 * {@code @RequiredArgsConstructor} Habilita a injeção de dependência via construtor com argumentos obrigatórios.
 *
 * @author juliane.maran
 * @see AuthenticationProvider
 */
@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Tenta autenticar um usuário com base nas credenciais fornecidas.
     *
     * <p>Este método é chamado pelo Spring Security durante o processo de autenticação.
     * Ele recebe um objeto {@link Authentication} que contém o username e a senha fornecidos pelo usuário.
     *
     * <p>O método realiza as seguintes etapas: <br>
     * 1. Extrai o username e a senha do objeto {@link Authentication}. <br> 2. Busca o usuário no repositório usando o
     * username. <br> 3. Se o usuário não for encontrado, lança uma exceção {@link BadCredentialsException}. <br> 4.
     * Verifica se a senha fornecida corresponde à senha armazenada do usuário usando o {@link PasswordEncoder}. <br> -
     * Se a senha não corresponder, lança uma exceção {@link BadCredentialsException}. <br> 5. Cria uma lista de
     * autoridades ({@link GrantedAuthority}) atribuindo todas as roles possíveis usando {@code Role.values()}. <br> - A
     * conversão para string usando {@code Arrays.toString(Role.values())} é necessária para o formato esperado pelo
     * construtor de {@link UsernamePasswordAuthenticationToken}. <br> 6. Retorna um novo objeto
     * {@link UsernamePasswordAuthenticationToken} contendo o username, a senha e as autoridades.
     *
     * @param authentication
     *         Objeto de autenticação contendo o username e a senha fornecidos pelo usuário.
     *
     * @return Um objeto {@link Authentication} representando o usuário autenticado com as authorities associadas, ou
     * {@code null} se a autenticação falhar.
     *
     * @throws AuthenticationException
     *         Se ocorrer um erro durante a autenticação, como usuário não encontrado ou senha inválida.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<User> customer = userRepository.findByUsername(username);
        if (customer.isEmpty()) {
            if (passwordEncoder.matches(pwd, customer.get().getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd,
                        getGrantedAuthorities(customer.get().getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details!");
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    /**
     * Indica se este provedor suporta o tipo de objeto de autenticação fornecida.
     *
     * <p>Este método verifica se a classe do objeto de autenticação é compatível com
     * {@link UsernamePasswordAuthenticationToken}. O provedor só pode autenticar usuários que forneceram credenciais de
     * username e password.</p>
     *
     * @param authentication
     *         A classe do objeto de autenticação a ser verificada.
     *
     * @return {@code true} se o provedor suporta o tipo de autenticação, {@code false} caso contrário
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
