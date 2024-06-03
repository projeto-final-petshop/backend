package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.exceptions.runtimes.RequiredFieldException;
import br.com.finalproject.petconnect.exceptions.runtimes.role.RoleNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserAlreadyExistsException;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String REQUIRED_FIELD_MESSAGE = "exception.validation.required_field";

    public User createAdministrator(UserRequest input) {

        Optional<Role> optionalRole = Optional.of(roleRepository.findByName(RoleEnum.ADMIN).orElseThrow());

        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .active(true)
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .role(optionalRole.get())
                .build();

        return userRepository.save(user);
    }

    public User createUserWithRole(UserRequest input, int roleCode) {
        RoleEnum roleEnum;
        String roleName = switch (roleCode) {
            case 3 -> {
                roleEnum = RoleEnum.GROOMING;
                yield "GROOMING";
            }
            case 4 -> {
                roleEnum = RoleEnum.VETERINARIAN;
                yield "VETERINARIAN";
            }
            case 5 -> {
                roleEnum = RoleEnum.EMPLOYEE;
                yield "EMPLOYEE";
            }
            default -> {
                log.error("Código de role inválido: {}", roleCode);
                throw new IllegalArgumentException("exception.validation.invalid_role_code");
            }
        };

        Role role = findRoleByEnum(roleEnum, roleName);
        return createUser(input, role);
    }

    private Role findRoleByEnum(RoleEnum roleEnum, String roleName) {
        log.info("Procurando role: {}", roleName);
        Optional<Role> optionalRole = roleRepository.findByName(roleEnum);
        return optionalRole.orElseThrow(() -> {
            log.error("Erro ao cadastrar usuário: role {} não encontrada", roleName);
            return new RoleNotFoundException("exception.permission.not_found");
        });
    }

    private User createUser(UserRequest input, Role role) {

        validateUserInput(input);

        if (userRepository.existsByEmail(input.getEmail())) {
            log.error("Erro ao criar usuário: email {} já está cadastrado", input.getEmail());
            throw new UserAlreadyExistsException("exception.user.email_already_exists");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            log.error("Erro ao criar usuário: CPF {} já está cadastrado", input.getCpf());
            throw new UserAlreadyExistsException("exception.user.cpf_already_exists");
        }

        log.info("Criando usuário com email: {}", input.getEmail());
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
        log.info("Usuário criado com sucesso: id {}", savedUser.getId());
        return savedUser;

    }

    private void validateUserInput(UserRequest input) {

        if (input.getName() == null || input.getName().isEmpty()) {
            log.error("Erro ao criar usuário: nome é um campo obrigatório");
            throw new RequiredFieldException(REQUIRED_FIELD_MESSAGE);
        }

        if (input.getEmail() == null || input.getEmail().isEmpty()) {
            log.error("Erro ao criar usuário: email é um campo obrigatório");
            throw new RequiredFieldException(REQUIRED_FIELD_MESSAGE);
        }

        if (input.getPassword() == null || input.getPassword().isEmpty()) {
            log.error("Erro ao criar usuário: senha é um campo obrigatório");
            throw new RequiredFieldException(REQUIRED_FIELD_MESSAGE);
        }

        if (input.getCpf() == null || input.getCpf().isEmpty()) {
            log.error("Erro ao criar usuário: CPF é um campo obrigatório");
            throw new RequiredFieldException(REQUIRED_FIELD_MESSAGE);
        }

    }

}