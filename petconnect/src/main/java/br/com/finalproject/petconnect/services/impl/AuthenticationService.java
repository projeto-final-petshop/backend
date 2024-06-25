package br.com.finalproject.petconnect.services.impl;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidCredentialsException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.UserInactiveException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.FieldAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.domain.entities.Role;
import br.com.finalproject.petconnect.domain.enums.RoleType;
import br.com.finalproject.petconnect.repositories.RoleRepository;
import br.com.finalproject.petconnect.domain.dto.request.LoginRequest;
import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.dto.response.UserResponse;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.mapping.UserMapper;
import br.com.finalproject.petconnect.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Serviço responsável por processar operações de autenticação, como registro de usuários.
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final Validator validator;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(UserRequest input) {

        validateInputUser(input);

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new FieldAlreadyExistsException("Email");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            throw new FieldAlreadyExistsException("CPF");
        }

        Role role = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new FieldNotFoundException("Role"));

        User user = UserMapper.INSTANCE.toUser(input);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return UserMapper.INSTANCE.toUserResponse(user);
    }

    private void validateInputUser(UserRequest input) {

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserRequest> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new InvalidRequestException(String.join(", ", errorMessages));
        }

        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

    }

    @Transactional
    public User authenticate(LoginRequest input) {
        validateInputLogin(input);

        User user = userRepository.findByEmail(input.getEmail()).orElseThrow(() -> {
            log.error("Usuário não encontrado: {}", input.getEmail());
            return new FieldNotFoundException("Email");
        });

        if (Boolean.FALSE.equals(user.getActive())) {
            log.error("Usuário inativo: {}", input.getEmail());
            throw new UserInactiveException();
        }

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            log.error("Senha incorreta para o usuário: {}", input.getEmail());
            throw new InvalidRequestException("Senha incorreta.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            log.error("Erro ao autenticar usuário: {}", input.getEmail());
            throw new InvalidCredentialsException();
        }

        return user;
    }

    private void validateInputLogin(LoginRequest input) {
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<LoginRequest> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new InvalidRequestException(String.join(", ", errorMessages));
        }
    }

}