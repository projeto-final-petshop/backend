package br.com.finalproject.petconnect.security.bootstrap;

import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.RegisterUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        log.info("Contexto inicializado. Iniciando o carregamento das roles.");
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {

        RegisterUserRequest request = RegisterUserRequest.builder()
                .name("PetConnect")
                .email("petshop.petconnect@gmail.com")
                .password(passwordEncoder.encode("P4$$w0rD"))
                .active(true)
                .cpf("396.810.991-09")
                .phoneNumber("+5521986548329")
                .build();

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            log.info("Superadministrador já existente ou a role de SUPER_ADMIN não foi encontrada. Pulando a criação do superadministrador.");
            return;
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.isActive())
                .cpf(request.getCpf())
                .phoneNumber(request.getPhoneNumber())
                .role(optionalRole.get())
                .build();

        userRepository.save(user);
        log.info("Superadministrador '{}' criado com sucesso!", user.getEmail());

    }

}
