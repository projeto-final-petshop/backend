package br.com.project.petconnect.app.petshop.repository;

import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetShopRepository extends JpaRepository<PetShopEntity, Long> {

    boolean existsByCnpj(String cnpj);

    List<PetShopEntity> findByBusinessNameContainingIgnoreCase(String businessName);

    Optional<PetShopEntity> findByCnpj(String cnpj);

    Optional<PetShopEntity> findByEmail(String email);

}
