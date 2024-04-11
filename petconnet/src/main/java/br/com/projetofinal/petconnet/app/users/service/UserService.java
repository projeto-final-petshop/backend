package br.com.projetofinal.petconnet.app.users.service;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.Users;
import br.com.projetofinal.petconnet.app.users.helper.UserHelper;
import br.com.projetofinal.petconnet.app.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Classe responsável por operações de usuário.
 * <p>
 * Encapsula a lógica de negócio relacionada a cadastro, busca, atualização, ativação e desativação de usuários.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    /**
     * Injeção de dependência da classe auxiliar para operações de usuário.
     */
    private final UserHelper userHelper;
    /**
     * Injeção de dependência do repositório de usuários.
     */
    private final UserRepository userRepository;

    /**
     * Cadastra um novo usuário.
     *
     * @param request
     *         Objeto contendo os dados do usuário a ser cadastrado.
     *
     * @return Retorna um objeto {@link RegisterUserResponse} contendo os dados do usuário cadastrado.
     * <p>
     * {@code UsernameAlreadyExistsException}: Se o username informado já existir.
     * <p>
     * {@code InvalidFieldException}: Se houver algum erro de validação nos dados do usuário.
     */
    @Transactional
    public RegisterUserResponse createUser(RegisterUserRequest request) {
        log.info("User Service --- Cadastrando um novo usuário com username {}",
                request.getUsername());
        userHelper.validateUsernameExists(request.getUsername());
        userHelper.validateDocumentNumberExists(request.getDocumentNumber());
        Users savedUser = userHelper.createUser(request);
        log.info("User Service --- Usuário cadastrado com sucesso: {}", savedUser);
        return UserMapper.userMapper().toRegisterUserResponse(savedUser);
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Retorna uma lista de objetos UserResponse contendo os dados de todos os usuários.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("User Service --- Listando todos os usuários");
        List<Users> users = userRepository.findAll();
        return userHelper.toUserResponseList(users);
    }

    /**
     * Busca um usuário por ID.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna um objeto {@link UserResponse} contendo os dados do usuário encontrado.
     * <p>
     * {@code UsernameNotFoundException} Se o usuário não for encontrado.
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("User Service --- Buscando usuário com ID: {}", id);
        Users user = userHelper.findUserById(id);
        return userHelper.toUserResponse(user);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id
     *         Identificador do usuário a ser atualizado.
     * @param request
     *         Objeto contendo os dados atualizados do usuário
     *         <p>
     *         {@code UsernameAlreadyExistsException} Se o novo username informado já existir.
     *         <p>
     *         {@code InactiveUserException} Se o usuário estiver inativo.
     */
    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        log.info("User Service --- Atualizando usuário com ID: {}", id);
        Users user = userHelper.findAndValidateUser(id);
        userHelper.updateUserFields(user, request);
        log.info("User Service --- Usuário atualizado com sucesso: {}", user);
        userHelper.handleUserUpdate(user);
    }

    /**
     * Busca um usuário por username.
     *
     * @param username
     *         Email do usuário.
     *
     * @return Retorna um objeto UserResponse contendo os dados do usuário encontrado.
     * <p>
     * {@code UsernameNotFoundException} Se o usuário não for encontrado.
     */
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        log.info("User Service --- Buscando usuário com username: {}", username);
        Users user = userHelper.findUserByUsername(username);
        return userHelper.toUserResponse(user);
    }

    /**
     * Desativa um usuário existente.
     *
     * @param id
     *         Identificador do usuário a ser desativado.
     *         <p>
     *         {@code UsernameNotFoundException} Se o usuário não for encontrado.
     *         <p>
     *         {@code FailedToInactivateUserException} Se ocorrer algum erro durante a desativação do usuário.
     */
    @Transactional
    public void disableUser(Long id) {
        log.info("User Service --- Desativando usuário: {}", id);
        Users user = userHelper.findUserAndHandleNotFound(id);
        userHelper.setUserActiveStatus(user, false);
    }

    /**
     * Ativa um usuário existente.
     *
     * @param id
     *         Identificador do usuário a ser ativado.
     *         <p>
     *         {@code UsernameNotFoundException} Se o usuário não for encontrado.
     *         <p>
     *         {@code FailedToInactivateUserException} Se ocorrer algum erro durante a ativação do usuário.
     */
    @Transactional
    public void activateUser(Long id) {
        log.info("User Service --- Ativando usuário: {}", id);
        Users user = userHelper.findUserAndHandleNotFound(id);
        userHelper.setUserActiveStatus(user, true);
    }

}
