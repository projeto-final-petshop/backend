package br.com.projetofinal.petconnet.app.users.repository;

import br.com.projetofinal.petconnet.app.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para operações com a entidade {@link Users}.
 * <p>
 * Provê métodos para persistir, recuperar e excluir usuários do banco de dados.
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String username);

    /**
     * Busca um usuário por username.
     *
     * @param username
     *         Email do usuáiro
     *
     * @return Retorna um {@link Optional} contendo o objeto {@link Users} encontrado, ou vazio se o usuário não for
     * encontrado.
     */
    Optional<Users> findByUsername(String username);

    Users findByUsernameAndPassword(String username, String password);

    Optional<Users> findByDocumentNumber(String documentNumber);

}
