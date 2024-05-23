package br.com.project.petconnect.security.bootstrap;

import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import br.com.project.petconnect.security.repository.RoleRepository;
import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.repository.UserRepository;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {

        var userRequest = new UserRequest();
        userRequest.setName("PetConnect");
        userRequest.setEmail("petshop.petconnect@gmail.com");
        userRequest.setPassword("P3tC0nn&ct");
        userRequest.setCpf("056.848.945-42"); // cpf gerado aleatoriamente
        userRequest.setPhoneNumber("61995561092"); // número de telefone gerado aleatoriamente

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            log.info("Super Admin já existe ou role SUPER_ADMIN não encontrada. Ignorando/Cancelando criação.");
            return;
        }

        var user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCpf(userRequest.getCpf());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRole(optionalRole.get());

        userRepository.save(user);
        log.info("Super Admin '{}' criado com sucesso!", user.getEmail());

    }

}
