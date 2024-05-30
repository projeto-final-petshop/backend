package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.exceptions.runtimes.RoleNotFoundException;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.request.RegisterUserRequest;
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

    public User createAdministrator(RegisterUserRequest input) {
        log.info("Iniciando processo de cadastro de administrador para o email: {}", input.getEmail());
        Role role = findRoleByEnum(RoleEnum.ADMIN, "ADMIN");
        return createUser(input, role);
    }

    public User createEmployeeOrVeterinarian(RegisterUserRequest input) {
        log.info("Iniciando processo de cadastro para o email: {} ", input.getEmail());
        Role role = findRoleByEnum(RoleEnum.ADMIN, "ADMIN");
        return createUser(input, role);
    }

    private Role findRoleByEnum(RoleEnum roleEnum, String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleEnum);
        return optionalRole.orElseThrow(() -> {
            log.error("Erro ao cadastrar usuário: role {} não encontrada", roleName);
            return new RoleNotFoundException("Role " + roleName + " não encontrada");
        });
    }

    private User createUser(RegisterUserRequest input, Role role) {
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

}
