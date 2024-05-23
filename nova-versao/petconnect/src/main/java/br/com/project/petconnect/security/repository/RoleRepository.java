package br.com.project.petconnect.security.repository;

import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}
