package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.CpfNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.NoInactiveUsersFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.*;

@Slf4j
@AllArgsConstructor
public class UserServiceUtils {

    static UserRepository userRepository;
    static MessageUtil messageUtil;

    public static String formatMessage(String key, String param) {
        return messageUtil.getMessage(key) + ": " + param;
    }

    public static void updateUserFields(User user, UserRequest userRequest) {
        updateName(user, userRequest);
        updateEmail(user, userRequest);
        updateCpf(user, userRequest);
        updatePhoneNumber(user, userRequest);
    }

    public static void updatePhoneNumber(User user, UserRequest userRequest) {
        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
    }

    public static void updateCpf(User user, UserRequest userRequest) {
        if (userRequest.getCpf() != null && !userRequest.getCpf().isEmpty()) {
            user.setCpf(userRequest.getCpf());
        }
    }

    public static void updateEmail(User user, UserRequest userRequest) {
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }
    }

    public static void updateName(User user, UserRequest userRequest) {
        if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
            user.setName(userRequest.getName());
        }
    }

    public static User findFirstInactiveUserOrThrowException(FindUserRequest request) {
        List<User> inactiveUsers = findUsersByActive(false);
        if (!inactiveUsers.isEmpty()) {
            return inactiveUsers.getFirst();
        }
        throw new NoInactiveUsersFoundException(formatMessage(NO_INACTIVE_USERS_FOUND_MESSAGE,
                request.toString()));
    }

    public static User findUserByEmailOrThrowException(String email) {
        return findUserByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(formatMessage(NOT_FOUND_EMAIL_MESSAGE, email)));
    }

    public static User findUserByCpfOrThrowException(String cpf) {
        return findUserByCpf(cpf)
                .orElseThrow(() -> new CpfNotFoundException(formatMessage(NOT_FOUND_CPF_MESSAGE, cpf)));
    }

    public static User findUserByName(String name) {
        return findAndLogUser("Buscando usuário pelo nome: {}", name,
                userRepository.findByName(name).stream().findFirst());
    }

    public static Optional<User> findUserByEmail(String email) {
        return findAndLogOptionalUser("Buscando usuário pelo email: {}", email,
                userRepository.findByEmail(email));
    }

    public static Optional<User> findUserByCpf(String cpf) {
        return findAndLogOptionalUser("Buscando usuário pelo CPF: {}", cpf,
                userRepository.findByCpf(cpf));
    }

    public static List<User> findUsersByActive(boolean active) {
        return findAndLogUsers("Listando usuários com status ativo: {}",
                userRepository.findByActive(active), "Total de usuários com status ativo {}: {}");
    }

    public static String changeUserActiveStatus(Long id, boolean active, String startLogMessage,
                                                String successLogMessage, String successMessageKey) {
        User user = getUserAndLog(id, startLogMessage);
        user.setActive(active);
        userRepository.save(user);
        log.info(successLogMessage, id);
        return messageUtil.getMessage(successMessageKey);
    }

    public static User getUserAndLog(Long id, String logMessage) {
        log.info(logMessage, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(NOT_FOUND_USER_MESSAGE)));
    }

    public static User findAndLogUser(String messageTemplate, String arg, Optional<User> userOptional) {
        log.info(messageTemplate, arg);
        return userOptional.orElseGet(() -> {
            log.warn("Nenhum usuário encontrado com {}", arg);
            return null;
        });
    }

    public static Optional<User> findAndLogOptionalUser(String messageTemplate, String arg, Optional<User> userOptional) {
        log.info(messageTemplate, arg);
        if (userOptional.isPresent()) {
            log.info("Usuário encontrado: {}", userOptional.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com {}", arg);
        }
        return userOptional;
    }

    public static List<User> findAndLogUsers(String startLogMessage, List<User> users, String endLogMessage) {
        log.info(startLogMessage);
        List<User> userList = new ArrayList<>(users);
        log.info(endLogMessage, userList.size());
        return userList;
    }

    public static User findAndLogUserById(Long id, String logMessage, String notFoundMessageKey) {
        log.info(logMessage, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(notFoundMessageKey)));
    }

    public static User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage(NOT_FOUND_USER_MESSAGE)));
    }

}
