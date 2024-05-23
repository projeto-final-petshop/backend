package br.com.project.petconnect.app.owner.repository;

import br.com.project.petconnect.app.owner.domain.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
}
