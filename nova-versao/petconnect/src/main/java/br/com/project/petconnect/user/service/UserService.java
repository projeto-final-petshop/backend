package br.com.project.petconnect.user.service;

import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.entities.Pet;
import br.com.project.petconnect.pet.repository.PetRepository;
import br.com.project.petconnect.security.entities.Role;
import br.com.project.petconnect.security.entities.RoleEnum;
import br.com.project.petconnect.security.repository.RoleRepository;
import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.dto.UserResponse;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.mapping.UserMapper;
import br.com.project.petconnect.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por operações relacionadas aos usuários do sistema.
 */
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PetRepository petRepository;
    private final UserHelper userHelper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       PetRepository petRepository,
                       UserHelper userHelper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.petRepository = petRepository;
        this.userHelper = userHelper;
    }

    // listUsers - GET /users/list
    public List<User> allUsers() {
        log.info("Buscando todos os usuários....");
        List<User> users = userRepository.findAll();
        log.info("Usuários encontrados: {}", users.size());
        return users;
    }

    public User createAdministrator(UserRequest input) {
        log.info("Criando administrador....");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isPresent()) {
            log.warn("A função Administrador já existe. Não é possível criar outro.");
            return null;
        }

        var user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .build();

        log.info("Administrador criado: {}", user.getName());
        return userRepository.save(user);

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // TODO: Implementar - updateUser - PUT /users/{id}
    @Transactional
    public void updateUser(Long id, UserRequest request) {
        log.info("[ User Service - updateUser ] --- Atualizando usuário com ID: {}", id);
        User user = userHelper.findAndValidateUser(id);
        userHelper.updateUserFields(user, request);
        log.info("[ User Service - updateUser ] --- Usuário atualizado com sucesso: {}", user);
        userHelper.handleUserUpdate(user);
    }

    // TODO: Implementar - getUserById - GET /users/{id}
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("[ User Service - getUserById ] --- Buscando usuário com ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[ User Service - getUserById ] --- Usuário com o ID {} não encontrado.", id);
                    return new UsernameNotFoundException("Usuário não encontrado");
                });
        return UserMapper.userMapper().toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        log.info("[ User Service - getUserByUsername ] --- Buscando usuário com username: {}", username);
        User user = userHelper.findUserByUsername(username);
        return userHelper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserAndPets(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[ User Service - getUserAndPets ] --- Usuário com o ID {} não encontrado.", userId);
                    return new UsernameNotFoundException("Usuário não encontrado");
                });

        List<Pet> petsList = petRepository.findByUserId(userId);

        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : petsList) {
            PetResponse build = PetResponse.builder()
                    .id(pet.getId())
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .color(pet.getColor())
                    .animalType(pet.getAnimalType())
                    .createdAt(pet.getCreatedAt())
                    .build();
            petResponses.add(build);
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .pets(petResponses)
                .build();

    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[ User Service - deleteUser ] --- Usuário com o ID {} não encontrado.", userId);
                    return new UsernameNotFoundException("Usuário não encontrado.");
                });
        userRepository.delete(user);
    }

}
