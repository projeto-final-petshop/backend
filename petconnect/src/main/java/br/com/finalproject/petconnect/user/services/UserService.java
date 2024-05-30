package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.NoSearchCriteriaProvidedException;
import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "notFound.user";
    private final UserServiceUtils userServiceUtils;
    private final UserRepository userRepository;

    @Transactional
    public void updateUser(String email, UserRequest userRequest) {
        final var existingUser = userServiceUtils.getUserByEmail(email);
        userServiceUtils.updateUserFields(existingUser, userRequest);
        User savedUser = userRepository.save(existingUser);
        UserMapper.petMapper().toUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userServiceUtils.findAndLogUsers("Buscando todos os usuários",
                userRepository.findAll(), "Total de usuários encontrados: {}");
    }

    @Transactional(readOnly = true)
    public User findUser(FindUserRequest request) {
        if (request.getName() != null) {
            return userServiceUtils.findUserByName(request.getName());
        }
        if (request.getEmail() != null) {
            return userServiceUtils.findUserByEmailOrThrowException(request.getEmail());
        }
        if (request.getCpf() != null) {
            return userServiceUtils.findUserByCpfOrThrowException(request.getCpf());
        }
        if (!request.isActive()) {
            return userServiceUtils.findFirstInactiveUserOrThrowException(request);
        }
        throw new NoSearchCriteriaProvidedException(userServiceUtils
                .formatMessage("badRequest.noSearchCriteriaProvided", request.toString()));
    }

    @Transactional(readOnly = true)
    public List<User> listUsersByName(String name) {
        return userServiceUtils.findAndLogUsers("Listando usuários pelo nome: {}",
                userRepository.findByName(name), "Total de usuários encontrados com o nome {}: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listActiveUsers() {
        return userServiceUtils.findAndLogUsers("Listando usuários ativos",
                userRepository.findByActive(true), "Total de usuários ativos: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listInactiveUsers() {
        return userServiceUtils.findAndLogUsers("Listando usuários inativos",
                userRepository.findByActive(false), "Total de usuários inativos: {}");
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userServiceUtils.findAndLogUserById(id, "Buscando usuário com ID: {}",
                USER_NOT_FOUND);
    }

    @Transactional
    public void deleteUser(String email) {
        final var user = userServiceUtils.getUserByEmail(email);
        userRepository.delete(user);
    }

}
