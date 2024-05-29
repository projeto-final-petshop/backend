package br.com.finalproject.petconnect.pets.repositories;

import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByIdAndUser(Long id, User user);

    List<Pet> findByUser(User user);

}
