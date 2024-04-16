package br.com.projetofinal.petconnet.app.users.service;

import br.com.projetofinal.petconnet.app.pets.dto.respose.PetListResponse;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserListResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.Users;
import br.com.projetofinal.petconnet.app.users.helper.UserHelper;
import br.com.projetofinal.petconnet.app.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final PetRepository petRepository;

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
        log.info("[ User Service - createUser ] --- Cadastrando um novo usuário com username {}",
                request.getUsername());
        userHelper.validateUsernameExists(request.getUsername());
        userHelper.validateDocumentNumberExists(request.getDocumentNumber());
        Users savedUser = userHelper.createUser(request);
        log.info("[ User Service - createUser ] --- Usuário cadastrado com sucesso: {}", savedUser);
        return UserMapper.userMapper().toRegisterUserResponse(savedUser);
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Retorna uma lista de objetos UserResponse contendo os dados de todos os usuários.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("[ User Service - getAllUsers ] --- Listando todos os usuários");
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
        log.info("[ User Service - getUserById ] --- Buscando usuário com ID: {}", id);
        Users user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[ User Service - getUserById ] --- Usuário com o ID {} não encontrado.", id);
                    return new UsernameNotFoundException();
                });
        return UserMapper.userMapper().toUserResponse(user);
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
        log.info("[ User Service - updateUser ] --- Atualizando usuário com ID: {}", id);
        Users user = userHelper.findAndValidateUser(id);
        userHelper.updateUserFields(user, request);
        log.info("[ User Service - updateUser ] --- Usuário atualizado com sucesso: {}", user);
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
        log.info("[ User Service - getUserByUsername ] --- Buscando usuário com username: {}", username);
        Users user = userHelper.findUserByUsername(username);
        return userHelper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserListResponse getUserAndPets(Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[ User Service - getUserAndPets ] --- Usuário com o ID {} não encontrado.", userId);
                    return new UsernameNotFoundException();
                });

        List<Pets> petsList = petRepository.findByUserId(userId);

        List<PetListResponse> petResponses = new ArrayList<>();
        for (Pets pet : petsList) {
            PetListResponse build = PetListResponse.builder()
                    .id(pet.getId())
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .color(pet.getColor())
                    .birthdate(pet.getBirthdate())
                    .animalType(pet.getAnimalType())
                    .createdAt(pet.getCreatedAt())
                    .updatedAt(pet.getUpdatedAt())
                    .build();
            petResponses.add(build);
        }

        return UserListResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .pets(petResponses)
                .build();

    }

    @Transactional
    public void deleteUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[ User Service - deleteUser ] --- Usuário com o ID {} não encontrado.", userId);
                    return new UsernameNotFoundException();
                });
        userRepository.delete(user);
    }

}
