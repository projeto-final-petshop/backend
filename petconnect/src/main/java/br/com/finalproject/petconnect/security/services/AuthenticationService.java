package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.role.RoleNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserAlreadyExistsException;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final MessageUtil messageUtil;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(UserRequest input) {

        log.info("Iniciando processo de cadastro para o email: {}", input.getEmail());

        if (userRepository.existsByEmail(input.getEmail())) {
            log.error("Erro ao cadastrar usuário: email {} já está em uso", input.getEmail());
            throw new UserAlreadyExistsException("Email já está em uso");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            log.error("Erro ao cadastrar usuário: CPF {} já está em uso", input.getCpf());
            throw new UserAlreadyExistsException("CPF já está em uso");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            log.error("Erro ao cadastrar usuário: role USER não encontrada");
            throw new RoleNotFoundException("Role USER não encontrada");
        }

        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .active(true)
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .role(optionalRole.get())
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuário cadastrado com sucesso: {}", savedUser.getId());
        return savedUser;
    }

    public User authenticate(LoginRequest input) {
        log.info("Iniciando processo de autenticação para o email: {}", input.email());

        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com o email: {}", input.email());
                    return new UsernameNotFoundException(messageUtil.getMessage("usernameNotFound"));
                });

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.email(),
                            input.password()
                    )
            );
            log.info("Autenticação bem-sucedida para o email: {}", input.email());
        } catch (Exception e) {
            log.error("Falha na autenticação para o email: {}", input.email(), e);
            throw e;
        }

        log.info("Usuário autenticado com sucesso: {}", user.getId());
        return user;
    }

}