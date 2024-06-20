package br.com.finalproject.petconnect.pets.repositories;

import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByUser(User user);

    Optional<Pet> findByIdAndUserId(Long petId, Long userId);

    @Modifying
    @Query("DELETE FROM Pet p WHERE p.user.id = :userId")
    void deleteByUserId(Long userId);

}
