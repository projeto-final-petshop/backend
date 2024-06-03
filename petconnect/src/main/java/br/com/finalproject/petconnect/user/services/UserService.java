package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.user.InvalidUserDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.NOT_FOUND_USER_MESSAGE;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final PetRepository petRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Atualizar Usuário
     */
    @Transactional
    public void updateUser(String email, UserRequest userRequest) {

        User user = userRepository.findByEmail(email).orElseThrow();

        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }

        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }

        if (userRequest.getAddress() != null) {
            user.setAddress(userRequest.getAddress());
        }

        User savedUser = userRepository.save(user);

        UserMapper.INSTANCE.toUserResponse(savedUser);

    }

    /**
     * Excluir usuário
     */
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        petRepository.deleteByUserId(user.getId());
        userRepository.delete(user);
    }

    /**
     * Buscar usuário por Email
     */
    @Transactional
    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    /**
     * Buscar usuário por CPF
     */
    @Transactional
    public UserResponse findUserByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf).orElseThrow();
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    /**
     * Listar todos os usuários cadastrados
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.toUserResponseList(users);
    }

    /**
     * Buscar usuários ativos
     */
    public List<UserResponse> findActiveUsers() {
        List<User> users = userRepository.findByActiveTrue();
        return UserMapper.INSTANCE.toUserResponseList(users);
    }

    /**
     * Buscar usuários inativos
     */
    public List<UserResponse> findInactiveUsers() {
        List<User> users = userRepository.findByActiveFalse();
        return UserMapper.INSTANCE.toUserResponseList(users);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, String email, String cpf, Boolean active, Pageable pageable) {
        Page<User> usersPage = userRepository
                .findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndCpfContainingIgnoreCaseAndActive(
                        name, email, cpf, active, pageable);
        return usersPage.map(userMapper::toUserResponse);
    }

    @Transactional(readOnly = true)
    public User findUser(FindUserRequest request) {
        if (request.getName() != null) {
            return UserServiceUtils.findUserByName(request.getName());
        }
        if (request.getEmail() != null) {
            return UserServiceUtils.findUserByEmailOrThrowException(request.getEmail());
        }
        if (request.getCpf() != null) {
            return UserServiceUtils.findUserByCpfOrThrowException(request.getCpf());
        }
        if (!request.isActive()) {
            return UserServiceUtils.findFirstInactiveUserOrThrowException(request);
        }
        throw new InvalidUserDataException(UserServiceUtils
                .formatMessage("NO_SEARCH_CRITERIA_PROVIDED_MESSAGE", request.toString()));
    }

    @Transactional(readOnly = true)
    public List<User> listUsersByName(String name) {
        return UserServiceUtils.findAndLogUsers("Listando usuários pelo nome: {}",
                userRepository.findByName(name), "Total de usuários encontrados com o nome {}: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listActiveUsers() {
        return UserServiceUtils.findAndLogUsers("Listando usuários ativos",
                userRepository.findByActive(true), "Total de usuários ativos: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listInactiveUsers() {
        return UserServiceUtils.findAndLogUsers("Listando usuários inativos",
                userRepository.findByActive(false), "Total de usuários inativos: {}");
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return UserServiceUtils.findAndLogUserById(id, "Buscando usuário com ID: {}",
                NOT_FOUND_USER_MESSAGE);
    }

}
