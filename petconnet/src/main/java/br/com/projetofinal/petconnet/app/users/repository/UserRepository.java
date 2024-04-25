package br.com.projetofinal.petconnet.app.users.repository;

import br.com.projetofinal.petconnet.app.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para operações com a entidade {@link User}.
 * <p>
 * Provê métodos para persistir, recuperar e excluir usuários do banco de dados.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    /**
     * Busca um usuário por username.
     *
     * @param username
     *         Email do usuáiro
     *
     * @return Retorna um {@link Optional} contendo o objeto {@link User} encontrado, ou vazio se o usuário não for
     * encontrado.
     */
    Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByCpf(String documentNumber);

}
