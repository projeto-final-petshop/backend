package br.com.finalproject.petconnect.user.repositories;

import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    List<User> findByName(String name);

    List<User> findByActive(boolean active);

    Optional<User> findById(Long id);

}
