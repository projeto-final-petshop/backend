package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.help.RoleTypeEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleTypeRepository extends JpaRepository<RoleTypeEntities, Long> {
}
