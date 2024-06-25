package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.help.PetTypeEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetTypeEntities, Long> {
}
