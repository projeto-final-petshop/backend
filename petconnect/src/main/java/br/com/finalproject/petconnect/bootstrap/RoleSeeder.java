package br.com.finalproject.petconnect.bootstrap;

import br.com.finalproject.petconnect.domain.entities.Role;
import br.com.finalproject.petconnect.domain.enums.RoleType;
import br.com.finalproject.petconnect.repositories.RoleRepository;
import br.com.finalproject.petconnect.utils.constants.ConstantsUtil;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        log.info("Contexto inicializado. Iniciando o carregamento das roles.");
        this.loadRoles();
    }

    private void loadRoles() {

        log.info("Carregando roles...");

        RoleType[] roleNames = RoleType.values();

        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.USER, ConstantsUtil.USER_DESCRIPTION,
                RoleType.ADMIN, ConstantsUtil.ADMIN_DESCRIPTION,
                RoleType.GROOMING, ConstantsUtil.GROOMING_DESCRIPTION,
                RoleType.VETERINARIAN, ConstantsUtil.VETERINARIAN_DESCRIPTION,
                RoleType.EMPLOYEE, ConstantsUtil.EMPLOYEE_DESCRIPTION
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            log.info("Verificando se a role '{}' já existe no banco de dados.", roleName);
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(
                    role -> log.info("Role '{}' já existe: {}", roleName, role),
                    () -> {
                        log.info("Role '{}' não encontrada. Criando nova role.", roleName);
                        var roleToCreate = new Role();
                        // roleToCreate.setName(roleName);
                        roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                        Role savedRole = roleRepository.save(roleToCreate);
                        log.info("Role '{}' criada com sucesso: {}", roleName, savedRole);
                    }
            );

        });

        log.info("Carregamento das roles concluído.");

    }

}
