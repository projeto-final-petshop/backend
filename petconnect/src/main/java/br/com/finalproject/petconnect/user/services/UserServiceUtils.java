package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.*;
import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class UserServiceUtils {

    private static final String USER_NOT_FOUND = "notFound.user";
    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    public String formatMessage(String key, String param) {
        return messageUtil.getMessage(key) + ": " + param;
    }

    public void updateUserFields(User user, UserRequest userRequest) {
        updateName(user, userRequest);
        updateEmail(user, userRequest);
        updateCpf(user, userRequest);
        updatePhoneNumber(user, userRequest);
    }

    private void updatePhoneNumber(User user, UserRequest userRequest) {
        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
    }

    private void updateCpf(User user, UserRequest userRequest) {
        if (userRequest.getCpf() != null && !userRequest.getCpf().isEmpty()) {
            user.setCpf(userRequest.getCpf());
        }
    }

    private void updateEmail(User user, UserRequest userRequest) {
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }
    }

    public void updateName(User user, UserRequest userRequest) {
        if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
            user.setName(userRequest.getName());
        }
    }

    public User findFirstInactiveUserOrThrowException(FindUserRequest request) {
        List<User> inactiveUsers = findUsersByActive(false);
        if (!inactiveUsers.isEmpty()) {
            return inactiveUsers.getFirst();
        }
        throw new NoInactiveUsersFoundException(formatMessage("notFound.noInactiveUsersFound", request.toString()));
    }

    public User findUserByEmailOrThrowException(String email) {
        return findUserByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(formatMessage("notFound.email", email)));
    }

    public User findUserByCpfOrThrowException(String cpf) {
        return findUserByCpf(cpf)
                .orElseThrow(() -> new CpfNotFoundException(formatMessage("notFound.cpf", cpf)));
    }

    public User findUserByName(String name) {
        return findAndLogUser("Buscando usuário pelo nome: {}", name,
                userRepository.findByName(name).stream().findFirst());
    }

    private Optional<User> findUserByEmail(String email) {
        return findAndLogOptionalUser("Buscando usuário pelo email: {}", email,
                userRepository.findByEmail(email));
    }

    private Optional<User> findUserByCpf(String cpf) {
        return findAndLogOptionalUser("Buscando usuário pelo CPF: {}", cpf,
                userRepository.findByCpf(cpf));
    }

    private List<User> findUsersByActive(boolean active) {
        return findAndLogUsers("Listando usuários com status ativo: {}",
                userRepository.findByActive(active), "Total de usuários com status ativo {}: {}");
    }

    public String changeUserActiveStatus(Long id, boolean active, String startLogMessage,
                                         String successLogMessage, String successMessageKey) {
        User user = getUserAndLog(id, startLogMessage);
        user.setActive(active);
        userRepository.save(user);
        log.info(successLogMessage, id);
        return messageUtil.getMessage(successMessageKey);
    }

    private User getUserAndLog(Long id, String logMessage) {
        log.info(logMessage, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(USER_NOT_FOUND)));
    }

    public User findAndLogUser(String messageTemplate, String arg, Optional<User> userOptional) {
        log.info(messageTemplate, arg);
        return userOptional.orElseGet(() -> {
            log.warn("Nenhum usuário encontrado com {}", arg);
            return null;
        });
    }

    public Optional<User> findAndLogOptionalUser(String messageTemplate, String arg, Optional<User> userOptional) {
        log.info(messageTemplate, arg);
        if (userOptional.isPresent()) {
            log.info("Usuário encontrado: {}", userOptional.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com {}", arg);
        }
        return userOptional;
    }

    public List<User> findAndLogUsers(String startLogMessage, List<User> users, String endLogMessage) {
        log.info(startLogMessage);
        List<User> userList = new ArrayList<>(users);
        log.info(endLogMessage, userList.size());
        return userList;
    }

    public User findAndLogUserById(Long id, String logMessage, String notFoundMessageKey) {
        log.info(logMessage, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(notFoundMessageKey)));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(USER_NOT_FOUND)));
    }

}
