package br.com.project.petconnect.security.service;

import br.com.project.petconnect.security.dto.LoginRequest;
import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import br.com.project.petconnect.security.repository.RoleRepository;
import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(UserRequest userRequest) {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            log.warn("Role 'USER' não encontrado. Cadastro de usuário suspenso.");
            return null;
        }

        var user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .cpf(userRequest.getCpf())
                .phoneNumber(userRequest.getPhoneNumber())
                .role(optionalRole.get())
                .build();

        return userRepository.save(user);

    }

    public User authenticate(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        log.info("Usuário autenticado com sucesso: {}", loginRequest.getEmail());

        return userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

    }

}
