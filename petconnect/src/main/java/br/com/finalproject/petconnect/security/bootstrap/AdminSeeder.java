package br.com.finalproject.petconnect.security.bootstrap;

import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
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

        // Verifica se já existe algum usuário com a função de administrador
        if (userRepository.existsByRoleName(RoleEnum.ADMIN)) {
            log.info("Já existe pelo menos um administrador cadastrado.");
            return;
        }

        // Verifica se já existe algum usuário com o e-mail ou CPF fornecidos
        String email = "petshop.petconnect@gmail.com";
        String cpf = "396.810.991-09";

        if (userRepository.existsByEmailOrCpf(email, cpf)) {
            log.info("Já existe um usuário cadastrado com o mesmo e-mail ou CPF. Pulando a criação do administrador.");
            return;
        }

        // Carrega a role de ADMIN do banco de dados
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        if (optionalRole.isEmpty()) {
            log.error("Role de ADMIN não encontrada. Não é possível criar o administrador.");
            return;
        }

        // Cria o administrador
        UserRequest request = UserRequest.builder()
                .name("PetConnect")
                .email(email)
                .password(passwordEncoder.encode("P4$$w0rD"))
                .active(true)
                .cpf(cpf)
                .phoneNumber("+5521986548329")
                .build();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .active(request.isActive())
                .cpf(request.getCpf())
                .phoneNumber(request.getPhoneNumber())
                .role(optionalRole.get())
                .build();

        userRepository.save(user);
        log.info("Administrador '{}' criado com sucesso!", user.getEmail());

    }

}
