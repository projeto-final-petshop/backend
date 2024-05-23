package br.com.project.petconnect.user.service;

import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import br.com.project.petconnect.security.repository.RoleRepository;
import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por operações relacionadas aos usuários do sistema.
 */
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // listUsers - GET /users/list
    public List<User> allUsers() {
        log.info("Buscando todos os usuários....");
        List<User> users = userRepository.findAll();
        log.info("Usuários encontrados: {}", users.size());
        return users;
    }

    public User createAdministrator(UserRequest input) {
        log.info("Criando administrador....");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isPresent()) {
            log.warn("A função Administrador já existe. Não é possível criar outro.");
            return null;
        }

        var user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .build();

        log.info("Administrador criado: {}", user.getName());
        return userRepository.save(user);

    }

    // TODO: Implementar - updateUser - PUT /users/{id}

    // TODO: Implementar - getUserById - GET /users/{id}

    // TODO: Implementar - deleteUser - DELETE /users/{id}

}
