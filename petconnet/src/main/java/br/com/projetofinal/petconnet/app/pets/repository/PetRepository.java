package br.com.projetofinal.petconnet.app.pets.repository;

import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pets, Long> {

    Optional<Pets> findByName(String name);

}
