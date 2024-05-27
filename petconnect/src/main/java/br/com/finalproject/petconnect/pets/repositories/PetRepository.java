package br.com.finalproject.petconnect.pets.repositories;

import br.com.finalproject.petconnect.pets.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
