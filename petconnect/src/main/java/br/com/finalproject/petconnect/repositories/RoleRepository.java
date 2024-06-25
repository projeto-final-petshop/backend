package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.Role;
import br.com.finalproject.petconnect.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);

}
