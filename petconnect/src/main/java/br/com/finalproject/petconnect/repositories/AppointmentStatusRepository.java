package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.help.RoleTypeEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<RoleTypeEntities, Long> {
}
