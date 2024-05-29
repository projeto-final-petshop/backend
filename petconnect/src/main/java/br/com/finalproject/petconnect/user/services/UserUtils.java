package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
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
public class UserUtils {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    public String formatMessage(String key, String param) {
        return messageUtil.getMessage(key) + ": " + param;
    }

    public void updateUserFields(User user, UpdateUserRequest userUpdateRequest) {
        if (userUpdateRequest.getName() != null && !userUpdateRequest.getName().isEmpty()) {
            user.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getCpf() != null && !userUpdateRequest.getCpf().isEmpty()) {
            user.setCpf(userUpdateRequest.getCpf());
        }
        if (userUpdateRequest.getPhoneNumber() != null && !userUpdateRequest.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
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
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("USER_NOT_FOUND")));
    }

    public User findAndLogUser(String logMessage, String param, Optional<User> userOpt) {
        log.info(logMessage, param);
        return userOpt.orElseGet(() -> {
            log.warn("Nenhum usuário encontrado com {}", param);
            return null;
        });
    }

    public Optional<User> findAndLogOptionalUser(String logMessage, String param, Optional<User> userOpt) {
        log.info(logMessage, param);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com {}", param);
        }
        return userOpt;
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

}
