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

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.NOT_FOUND_USER_MESSAGE;
import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.NO_SEARCH_CRITERIA_PROVIDED_MESSAGE;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUser(String email, UserRequest userRequest) {
        final var existingUser = UserServiceUtils.getUserByEmail(email);
        UserServiceUtils.updateUserFields(existingUser, userRequest);
        User savedUser = userRepository.save(existingUser);
        UserMapper.petMapper().toUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return UserServiceUtils.findAndLogUsers("Buscando todos os usuários",
                userRepository.findAll(), "Total de usuários encontrados: {}");
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
        throw new NoSearchCriteriaProvidedException(UserServiceUtils
                .formatMessage(NO_SEARCH_CRITERIA_PROVIDED_MESSAGE, request.toString()));
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

    @Transactional
    public void deleteUser(String email) {
        final var user = UserServiceUtils.getUserByEmail(email);
        userRepository.delete(user);
    }

}
