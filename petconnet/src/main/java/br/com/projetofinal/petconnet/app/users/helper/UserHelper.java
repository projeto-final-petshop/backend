package br.com.projetofinal.petconnet.app.users.helper;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.Users;
import br.com.projetofinal.petconnet.app.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * @return Retorna o objeto {@link Users} representando o usuário cadastrado.
     *
     * @throws UsernameAlreadyExistsException
     *         Se o username informado já existir.
     * @throws InvalidFieldException
     *         Se houver algum erro de validação nos dados do usuário.
     */
    public Users createUser(RegisterUserRequest request) {
        Users user = UserMapper.userMapper().toUsers(request);
        activateUser(user);
        user.setCreatedAt(LocalDateTime.now());
        return saveUser(user);
    }

    /**
     * Converte um objeto Users para um objeto {@link RegisterUserResponse}. <br> Utilizado na resposta da API para
     * cadastro de usuário.
     *
     * @param savedUser
     *         Objeto {@link Users} representando o usuário cadastrado.
     *
     * @return Retorna o objeto {@link RegisterUserResponse} contendo os dados do usuário cadastrado.
     */
    public RegisterUserResponse toRegisterUserResponse(Users savedUser) {
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
        return userRepository.findByDocumentNumber(documentNumber).isPresent();
    }

    /**
     * Ativa o usuário recém-criado.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário a ser ativado.
     */
    private void activateUser(Users user) {
        user.setActive(true);
    }

    /**
     * Salva o usuário no banco de dados.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário a ser salvo.
     *
     * @return Retorna o objeto {@link Users} representando o usuário salvo no banco de dados.
     *
     * @throws InvalidFieldException
     *         Se houver algum erro de validação nos dados do usuário.
     */
    private Users saveUser(Users user) throws InvalidFieldException {
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
     * @return Retorna o objeto {@link Users} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public Users findUserById(Long id) {
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
     * @return Retorna o objeto {@link Users} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public Users findUserByUsername(String username) {
        log.info("User Service --- Buscando usuário com username {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User Service --- Usuário com o username {} não encontrado.", username);
                    return new UsernameNotFoundException();
                });
    }

    /**
     * Converte um objeto {@link Users} para um objeto {@link UserResponse}. <br> Utilizado na resposta da API para
     * operações de busca de usuário.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário.
     *
     * @return Retorna o objeto {@link UserResponse} contendo os dados do usuário.
     */
    public UserResponse toUserResponse(Users user) {
        return UserMapper.userMapper().toUserResponse(user);
    }

    /**
     * Converte uma lista de objetos {@link Users} para uma lista de objetos {@link UserResponse}. <br> Utilizado na
     * resposta da API para listagem de usuários.
     *
     * @param users
     *         Lista de objetos {@link Users} representando os usuários.
     *
     * @return Retorna a lista de objetos {@link UserResponse} contendo os dados dos usuários.
     */
    public List<UserResponse> toUserResponseList(List<Users> users) {
        return UserMapper.userMapper().toUserResponseList(users);
    }

    // -------------------- Métodos relacionados a atualização de usuário --------------------

    /**
     * Localiza e valida o usuário por ID antes de realizar a atualização.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna o objeto {@link Users} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public Users findAndValidateUser(Long id) {
        Users user = findUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException();
        }
        return user;
    }

    /**
     * Atualiza os campos do usuário com base nos dados informados.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário a ser atualizado.
     * @param request
     *         Objeto {@link UpdateUserRequest} contendo os dados de atualização.
     *
     * @throws UsernameAlreadyExistsException
     *         Se o novo username informado já existir.
     * @throws InactiveUserException
     *         Se o usuário estiver inativo.
     */
    public void updateUserFields(Users user, UpdateUserRequest request) {
        if (!user.getUsername().equals(request.getUsername())) {
            validateUsernameExists(request.getUsername());
            user.setUsername(request.getUsername());
        }
        validateUserIsActive(user);
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Trata a lógica de atualização do usuário e persisti os dados no banco.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário atualizado.
     *
     * @return Retorna o objeto {@link UserResponse} contendo os dados do usuário atualizado.
     */
    public UserResponse handleUserUpdate(Users user) {
        Users savedUser = userRepository.save(user);
        return UserMapper.userMapper().toUserResponse(savedUser);
    }

    /**
     * Valida se o usuário está ativo antes de realizar a atualização.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário.
     *
     * @throws InactiveUserException
     *         Se o usuário estiver inativo.
     */
    private void validateUserIsActive(Users user) {
        if (!Boolean.TRUE.equals(user.getActive())) {
            log.warn("User Service --- Usuário com ID {} está inativo.", user.getId());
            throw new InactiveUserException();
        }
    }

    // -------------------- Métodos relacionados a ativação e desativação de usuário --------------------

    /**
     * Localiza o usuário por ID antes de realizar a ativação ou desativação.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna o objeto {@link Users} representando o usuário encontrado.
     *
     * @throws UsernameNotFoundException
     *         Se o usuário não for encontrado.
     */
    public Users findUserAndHandleNotFound(Long id) {
        try {
            return findUserById(id);
        } catch (UsernameNotFoundException e) {
            log.error("User Service --- Usuário não encontrado com ID: {}", id, e);
            throw new UsernameNotFoundException();
        }
    }

    /**
     * Define o status de ativo ou inativo para o usuário.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário.
     * @param active
     *         Flag indicando se o usuário deve ser ativado (true) ou desativado (false).
     *
     * @throws FailedToInactivateUserException
     *         Se ocorrer algum erro durante a desativação do usuário.
     */
    public void setUserActiveStatus(Users user, boolean active) {
        try {
            user.setActive(active);
            userRepository.save(user);
            log.info("User Service --- Usuário {} com sucesso: {}",
                    (active ? "ativado" : "desativado"), user.getId());
        } catch (Exception e) {
            log.error("User Service --- Erro ao {} usuário: {}",
                    (active ? "ativar" : "desativar"), user.getId(), e);
            if (active) {
                throw new FailedToActivateUserException();
            }
            throw new FailedToInactivateUserException();
        }
    }

}
