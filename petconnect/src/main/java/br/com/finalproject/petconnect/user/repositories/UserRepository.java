package br.com.finalproject.petconnect.user.repositories;

import br.com.finalproject.petconnect.roles.entities.RoleEnum;
import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    List<User> findByName(String name);

    List<User> findByActive(boolean active);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByRoleName(RoleEnum roleEnum);

    boolean existsByEmailOrCpf(String email, String cpf);

    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    Page<User> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndCpfContainingIgnoreCaseAndActive(
            String name, String email, String cpf, Boolean active, Pageable pageable);

}
