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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por processar operações de autenticação, como registro de usuários.
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final MessageUtil messageUtil;

    /**
     * Repositório de papéis (roles) para recuperar informações sobre as funções dos usuários.
     */
    private final RoleRepository roleRepository;

    /**
     * Repositório de usuários para acessar e persistir informações de usuários.
     */
    private final UserRepository userRepository;

    /**
     * Encoder de senha para criptografar senhas antes de armazená-las no banco de dados.
     */
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Método responsável por registrar um novo usuário no sistema.
     *
     * @param input
     *         Os detalhes do usuário a ser registrado.
     *
     * @return O usuário recém-registrado em formato UserResponse.
     *
     * @throws UserAlreadyExistsException
     *         Se o e-mail ou CPF fornecido já estiverem em uso.
     * @throws RoleNotFoundException
     *         Se a função de usuário padrão não puder ser encontrada.
     */
    @Transactional
    public User signup(UserRequest input) {

        Role role = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new IllegalStateException("Role não encontrada."));

        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .active(true)
                .role(role)
                .address(input.getAddress())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

}