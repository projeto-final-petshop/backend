package br.com.finalproject.petconnect.roles.repositories;

import br.com.finalproject.petconnect.roles.entities.RoleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleTypeRepository extends JpaRepository<RoleTypeEntity, Long> {

    Optional<RoleTypeEntity> findByName(String name);
}
