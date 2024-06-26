package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidCredentialsException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.UserNotRegisteredException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.FieldAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
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

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(UserRequest input) {

        validateInput(input);

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new FieldAlreadyExistsException("Email");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            throw new FieldAlreadyExistsException("CPF");
        }

        Role role = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new FieldNotFoundException("Role"));

        User user = UserMapper.INSTANCE.toUser(input);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return UserMapper.INSTANCE.toUserResponse(user);
    }

    private void validateInput(UserRequest input) {
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
    }

    @Transactional
    public User authenticate(LoginRequest input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (InvalidCredentialsException e) {
            log.error("Erro ao autenticar usuário: {}", input.getEmail());
            throw new InvalidCredentialsException();
        }

        return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> {
            log.error("Usuário não encontrado: {}", input.getEmail());
            return new UserNotRegisteredException();
        });
    }

}