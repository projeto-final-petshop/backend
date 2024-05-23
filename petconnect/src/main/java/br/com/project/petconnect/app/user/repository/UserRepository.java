package br.com.project.petconnect.app.user.repository;

import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de acesso a dados de usuários.
 * <p>
 * Esta interface estende JpaRepository, que fornece métodos genéricos para operações CRUD.
 *
 * @author juliane.maran
 */
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Verifica se existe um usuário cadastrado com o e-mail informado.
     *
     * @param email
     *         E-mail a ser verificado.
     *
     * @return True se existir um usuário com o e-mail informado, false caso contrário.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe um usuário cadastrado com o CPF informado.
     *
     * @param cpf
     *         CPF a ser verificado.
     *
     * @return True se existir um usuário com o CPF informado, false caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Recupera uma lista de usuários com o e-mail informado.
     *
     * @param email
     *         E-mail a ser utilizado na busca.
     *
     * @return Lista contendo os usuários encontrados com o e-mail informado.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Verifica se existe um usuário cadastrado com o e-mail informado, considerando um identificador para ignorar o
     * e-mail do próprio usuário.
     *
     * @param email
     *         E-mail a ser verificado.
     * @param id
     *         Identificador do usuário (utilizado para ignorar o e-mail do próprio usuário).
     *
     * @return True se existir um usuário com o e-mail informado (considerando o filtro por identificador), false caso
     * contrário.
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Verifica se existe um usuário cadastrado com o CPF informado, considerando um identificador para ignorar o CPF do
     * próprio usuário.
     *
     * @param cpf
     *         CPF a ser verificado.
     * @param id
     *         Identificador do usuário (utilizado para ignorar o CPF do próprio usuário).
     *
     * @return True se existir um usuário com o CPF informado (considerando o filtro por identificador), false caso
     * contrário.
     */
    boolean existsByCpfAndIdNot(String cpf, Long id);

}
