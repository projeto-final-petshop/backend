package br.com.projetofinal.petconnet.users.entity;

import br.com.projetofinal.petconnet.pets.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pets, Long> {
}
