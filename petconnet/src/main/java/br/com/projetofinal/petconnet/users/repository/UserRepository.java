package br.com.projetofinal.petconnet.users.repository;

import br.com.projetofinal.petconnet.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
