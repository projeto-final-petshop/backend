package br.com.projetofinal.petconnet.app.users.helper;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe auxiliar para fornecer métodos utilitários relacionados a usuários.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserHelper {

    /**
     * Repositório de usuários injetado por construtor.
     */
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Valida se um nome de usuário já existe.
     *
     * @param username
     *         Email de usuário a ser verificado.
     *
     * @throws UsernameAlreadyExistsException
     *         Lançada se o nome de usuário já estiver em uso.
     */
    public void validateUsernameExists(String username) {
        if (usernameExists(username)) {
            log.warn("User Service --- Usuário com o email {} já existe.", username);
            throw new UsernameAlreadyExistsException();
        }
    }

    public void validateDocumentNumberExists(String documentNumber) {
        if (documentNumberExists(documentNumber)) {
            log.warn("User Service --- Usuário com o email {} já existe.", documentNumber);
            throw new DocumentNumberAlreadyExistsException();
        }
    }

    // -------------------- Métodos relacionados a cadastro de usuário --------------------

    /**
     * Cria um novo usuário a partir de um objeto {@link RegisterUserRequest}.
     *
     * @param request
     *         Objeto contendo os dados do usuário a ser cadastrado.
     *
     * @return Retorna o objeto {@link User} representando o usuário cadastrado.
     *
     * @throws UsernameAlreadyExistsException
     *         Se o username informado já existir.
     * @throws InvalidFieldException
     *         Se houver algum erro de validação nos dados do usuário.
     */
    public User createUser(RegisterUserRequest request) {
        User user = UserMapper.userMapper().toUsers(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return saveUser(user);
    }

    /**
     * Converte um objeto Users para um objeto {@link RegisterUserResponse}. <br> Utilizado na resposta da API para
     * cadastro de usuário.
     *
     * @param savedUser
     *         Objeto {@link User} representando o usuário cadastrado.
     *
     * @return Retorna o objeto {@link RegisterUserResponse} contendo os dados do usuário cadastrado.
     */
    public RegisterUserResponse toRegisterUserResponse(User savedUser) {
        return UserMapper.userMapper().toRegisterUserResponse(savedUser);
    }

    /**
     * Verifica se o username informado já existe no banco de dados.
     *
     * @param username
     *         Email de usuário a ser verificado.
     *
     * @return Retorna true se o username já existir, false caso contrário.
     */
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean documentNumberExists(String documentNumber) {
        return userRepository.findByCpf(documentNumber).isPresent();
    }

    /**
     * Salva o usuário no banco de dados.
     *
     * @param user
     *         Objeto {@link User} representando o usuário a ser salvo.
     *
     * @return Retorna o objeto {@link User} representando o usuário salvo no banco de dados.
     *
     * @throws InvalidFieldException
     *         Se houver algum erro de validação nos dados do usuário.
     */
    private User saveUser(User user) throws InvalidFieldException {
        try {
            return userRepository.save(user);
        } catch (InvalidFieldException e) {
            log.error("User Service --- Erro ao cadastrar usuário: {}", e.getMessage());
            throw new InvalidFieldException();
        }
    }

    // -------------------- Métodos relacionados a busca de usuário --------------------

    /**
     * Busca um usuário por ID.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna o objeto {@link User} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public User findUserById(Long id) {
        log.info("User Service --- Buscando usuário com ID {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User Service --- Usuário com o ID {} não encontrado.", id);
                    return new UsernameNotFoundException();
                });
    }

    /**
     * Busca um usuário por username.
     *
     * @param username
     *         Email de usuário.
     *
     * @return Retorna o objeto {@link User} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public User findUserByUsername(String username) {
        log.info("User Service --- Buscando usuário com username {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User Service --- Usuário com o username {} não encontrado.", username);
                    return new UsernameNotFoundException();
                });
    }

    /**
     * Converte um objeto {@link User} para um objeto {@link UserResponse}. <br> Utilizado na resposta da API para
     * operações de busca de usuário.
     *
     * @param user
     *         Objeto {@link User} representando o usuário.
     *
     * @return Retorna o objeto {@link UserResponse} contendo os dados do usuário.
     */
    public UserResponse toUserResponse(User user) {
        return UserMapper.userMapper().toUserResponse(user);
    }

    /**
     * Converte uma lista de objetos {@link User} para uma lista de objetos {@link UserResponse}. <br> Utilizado na
     * resposta da API para listagem de usuários.
     *
     * @param users
     *         Lista de objetos {@link User} representando os usuários.
     *
     * @return Retorna a lista de objetos {@link UserResponse} contendo os dados dos usuários.
     */
    public List<UserResponse> toUserResponseList(List<User> users) {
        return UserMapper.userMapper().toUserResponseList(users);
    }

    // -------------------- Métodos relacionados a atualização de usuário --------------------

    /**
     * Localiza e valida o usuário por ID antes de realizar a atualização.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna o objeto {@link User} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public User findAndValidateUser(Long id) {
        User user = findUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException();
        }
        return user;
    }

    /**
     * Atualiza os campos do usuário com base nos dados informados.
     *
     * @param user
     *         Objeto {@link User} representando o usuário a ser atualizado.
     * @param request
     *         Objeto {@link UpdateUserRequest} contendo os dados de atualização.
     *
     * @throws UsernameAlreadyExistsException
     *         Se o novo username informado já existir.
     * @throws InactiveUserException
     *         Se o usuário estiver inativo.
     */
    public void updateUserFields(User user, UpdateUserRequest request) {
        if (!user.getUsername().equals(request.getUsername())) {
            validateUsernameExists(request.getUsername());
            user.setUsername(request.getUsername());
        }
        user.setName(request.getName());
//        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Trata a lógica de atualização do usuário e persisti os dados no banco.
     *
     * @param user
     *         Objeto {@link User} representando o usuário atualizado.
     *
     * @return Retorna o objeto {@link UserResponse} contendo os dados do usuário atualizado.
     */
    public UserResponse handleUserUpdate(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.userMapper().toUserResponse(savedUser);
    }

    // -------------------- Métodos relacionados a ativação e desativação de usuário --------------------

    /**
     * Localiza o usuário por ID antes de realizar a ativação ou desativação.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna o objeto {@link User} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public User findUserAndHandleNotFound(Long id) {
        try {
            return findUserById(id);
        } catch (UsernameNotFoundException e) {
            log.error("User Service --- Usuário não encontrado com ID: {}", id, e);
            throw new UsernameNotFoundException();
        }
    }

}
