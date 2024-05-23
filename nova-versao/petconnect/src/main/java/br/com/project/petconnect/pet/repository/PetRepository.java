package br.com.project.petconnect.pet.repository;

import br.com.project.petconnect.pet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByName(String name);

    List<Pet> findByUserId(Long userId);

}
