package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.Pet;
import br.com.finalproject.petconnect.domain.entities.User;
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

    boolean existsByNameAndUser(String name, User user);

}
