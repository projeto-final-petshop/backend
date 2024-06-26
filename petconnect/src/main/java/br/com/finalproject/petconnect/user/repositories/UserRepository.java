package br.com.finalproject.petconnect.user.repositories;

import br.com.finalproject.petconnect.roles.entities.enums.RoleType;
import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    Page<User> findByActive(Boolean active, Pageable pageable);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.roles r WHERE r.name = :roleName")
    boolean existsByRoleName(RoleType roleName);

    boolean existsByEmailOrCpf(String email, String cpf);

    @Query("select u from User u " +
            "where (:name is null or u.name = :name) " +
            "and (:email is null or u.email = :email) " +
            "and (:cpf is null or u.cpf = :cpf) " +
            "and (:active is null or u.active = :active)")
    Page<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("cpf") String cpf,
                           @Param("active") Boolean active,
                           Pageable pageable);

}
