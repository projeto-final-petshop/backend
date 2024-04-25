package br.com.projetofinal.petconnet.core.security;

import br.com.projetofinal.petconnet.app.users.entity.Users;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@Primary
@AllArgsConstructor
public class ApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.info("User details - loadUserByUsername");

        Optional<Users> usersOptional = userRepository.findByUsername(username);

        if (usersOptional.isPresent()) {

            return Users.builder()
                    .username(username)
                    .password(usersOptional.get().getPassword())
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(usersOptional.get().getRole())))
                    .build();

        }

        throw new UsernameNotFoundException("Usuário não encontrado: " + username);

    }

}
