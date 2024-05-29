package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.*;
import br.com.finalproject.petconnect.exceptions.runtimes.NoSearchCriteriaProvidedException;
import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    private final UserUtils userUtils;
    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userUtils.findAndLogUsers("Buscando todos os usuários",
                userRepository.findAll(), "Total de usuários encontrados: {}");
    }

    @Transactional(readOnly = true)
    public User findUser(FindUserRequest request) {
        if (request.getName() != null) {
            return findUserByName(request.getName());
        }

        if (request.getEmail() != null) {
            return findUserByEmail(request.getEmail()).orElseThrow(() ->
                    new EmailNotFoundException(userUtils.formatMessage("EMAIL_NOT_FOUND", request.getEmail())));
        }

        if (request.getCpf() != null) {
            return findUserByCpf(request.getCpf()).orElseThrow(() ->
                    new CpfNotFoundException(userUtils.formatMessage("CPF_NOT_FOUND", request.getCpf())));
        }

        if (!request.isActive()) {
            List<User> inactiveUsers = findUsersByActive(false);
            if (!inactiveUsers.isEmpty()) {
                return inactiveUsers.getFirst();
            }
            throw new NoInactiveUsersFoundException(userUtils
                    .formatMessage("NO_INACTIVE_USERS_FOUND", request.toString()));
        }

        throw new NoSearchCriteriaProvidedException(userUtils
                .formatMessage("badRequest.noSearchCriteriaProvided", request.toString()));
    }

    @Transactional(readOnly = true)
    public User findUserByName(String name) {
        return userUtils.findAndLogUser("Buscando usuário pelo nome: {}", name,
                userRepository.findByName(name).stream().findFirst());
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userUtils.findAndLogOptionalUser("Buscando usuário pelo email: {}", email,
                userRepository.findByEmail(email));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByCpf(String cpf) {
        return userUtils.findAndLogOptionalUser("Buscando usuário pelo CPF: {}", cpf,
                userRepository.findByCpf(cpf));
    }

    @Transactional(readOnly = true)
    public List<User> listUsersByName(String name) {
        return userUtils.findAndLogUsers("Listando usuários pelo nome: {}",
                userRepository.findByName(name), "Total de usuários encontrados com o nome {}: {}");
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByActive(boolean active) {
        return userUtils.findAndLogUsers("Listando usuários com status ativo: {}",
                userRepository.findByActive(active), "Total de usuários com status ativo {}: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listActiveUsers() {
        return userUtils.findAndLogUsers("Listando usuários ativos",
                userRepository.findByActive(true), "Total de usuários ativos: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listInactiveUsers() {
        return userUtils.findAndLogUsers("Listando usuários inativos",
                userRepository.findByActive(false), "Total de usuários inativos: {}");
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userUtils.findAndLogUserById(id, "Buscando usuário com ID: {}",
                USER_NOT_FOUND);
    }

    @Transactional
    public void updateUser(String email, UpdateUserRequest userUpdateRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(USER_NOT_FOUND)));

        userUtils.updateUserFields(user, userUpdateRequest);
        userRepository.save(user);
    }

    @Transactional
    public String deactivateUser(Long id) {
        return userUtils.changeUserActiveStatus(id, false, "Desativando usuário com ID: {}",
                "Usuário com ID: {} desativado com sucesso", "DEACTIVATE_USER_SUCCESS");
    }

    @Transactional
    public String activateUser(Long id) {
        return userUtils.changeUserActiveStatus(id, true, "Ativando usuário com ID: {}",
                "Usuário com ID: {} ativado com sucesso", "ACTIVATE_USER_SUCCESS");
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(USER_NOT_FOUND)));
        userRepository.delete(user);
    }

}
