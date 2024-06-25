package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.help.ServiceTypeEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntities, Long> {
}
