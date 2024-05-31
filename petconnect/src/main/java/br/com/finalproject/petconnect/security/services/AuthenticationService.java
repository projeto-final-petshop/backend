package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.AuthenticationException;
import br.com.finalproject.petconnect.exceptions.runtimes.CpfAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.RoleNotFoundException;
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

    /**
     * Cadastrar um novo usuário
     */
    public User signup(UserRequest input) {
        log.info("Iniciando processo de cadastro para o email: {}", input.getEmail());
        validateUniqueEmail(input.getEmail());
        validateUniqueCpf(input.getCpf());
        Role role = findRole(RoleEnum.USER, "USER");
        return createUser(input, role);
    }

    /**
     * Autenticar usuário cadastrado - Login
     */
    public User authenticate(LoginRequest input) {
        log.info("Iniciando processo de autenticação para o email: {}", input.getEmail());
        authenticateUser(input.getEmail(), input.getPassword());
        return findUserByEmail(input.getEmail());
    }

    /**
     * Cadastrar um novo administrador
     * TODO: lógica deve ser utilizada para implementar o cadastro de funcionário (ROLE_EMPLOYEE)
     */
    public User createAdministrator(UserRequest input) {
        log.info("Iniciando processo de cadastro de administrador para o email: {}", input.getEmail());
        Role role = findRole(RoleEnum.ADMIN, "ADMIN");
        return createUser(input, role);
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.error("Erro ao cadastrar usuário: email {} já está em uso", email);
            throw new EmailAlreadyExistsException("Email já está em uso");
        }
    }

    private void validateUniqueCpf(String cpf) {
        if (userRepository.existsByCpf(cpf)) {
            log.error("Erro ao cadastrar usuário: CPF {} já está em uso", cpf);
            throw new CpfAlreadyExistsException("CPF já está em uso");
        }
    }

    private Role findRole(RoleEnum roleEnum, String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleEnum);
        if (optionalRole.isEmpty()) {
            log.error("Erro ao cadastrar usuário: role {} não encontrada", roleName);
            throw new RoleNotFoundException("Role " + roleName + " não encontrada");
        }
        return optionalRole.get();
    }

    private User createUser(UserRequest input, Role role) {
        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .active(true)
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        log.info("Usuário cadastrado com sucesso: {}", savedUser.getId());
        return savedUser;
    }

    private void authenticateUser(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            log.info("Autenticação bem-sucedida para o email: {}", email);
        } catch (AuthenticationException e) {
            log.error("Falha na autenticação para o email: {}", email, e);
            throw new AuthenticationException("Credenciais inválidas. Verifique seu e-mail");
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com o email: {}", email);
                    return new UsernameNotFoundException(messageUtil.getMessage("usernameNotFound"));
                });
    }

}