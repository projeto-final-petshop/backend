package br.com.project.petconnect.security.bootstrap;

import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import br.com.project.petconnect.security.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {

        var roleNames = new RoleEnum[]{
                RoleEnum.USER,
                RoleEnum.ADMIN,
                RoleEnum.SUPER_ADMIN
        };

        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Usuário padrão",
                RoleEnum.ADMIN, "Administrador",
                RoleEnum.SUPER_ADMIN, "Super Administrador"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                var roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToCreate);
            });
        });

    }

}
