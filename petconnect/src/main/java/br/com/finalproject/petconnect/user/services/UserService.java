package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.CpfNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return logAndReturnList("Buscando todos os usuários",
                userRepository.findAll(), "Total de usuários encontrados: {}");
    }

    @Transactional(readOnly = true)
    public User findUser(FindUserRequest request) {

        if (request.getName() != null) {
            return findUserByName(request.getName());
        }

        if (request.getEmail() != null) {
            return findUserByEmail(request.getEmail())
                    .orElseThrow(() -> new EmailNotFoundException(
                            messageUtil.getMessage("emailNotFound") + request.getEmail()));
        }

        if (request.getCpf() != null) {
            return findUserByCpf(request.getCpf())
                    .orElseThrow(() -> new CpfNotFoundException(
                            messageUtil.getMessage("cpfNotFound") + request.getCpf()));
        }

        if (!request.isActive()) {
            List<User> users = findUsersByActive(false);
            return users.isEmpty() ? null : users.getFirst();
        }

        return null;
    }

    @Transactional(readOnly = true)
    public User findUserByName(String name) {
        return logAndReturnUser(
                name, userRepository.findByName(name).stream().findFirst());
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return logAndReturnOptionalUser("Buscando usuário pelo email: {}",
                email, userRepository.findByEmail(email));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByCpf(String cpf) {
        return logAndReturnOptionalUser("Buscando usuário pelo CPF: {}",
                cpf, userRepository.findByCpf(cpf));
    }

    @Transactional(readOnly = true)
    public List<User> listUsersByName(String name) {
        return logAndReturnList(
                name, userRepository.findByName(name));
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByActive(boolean active) {
        return logAndReturnList(
                active, userRepository.findByActive(active));
    }

    @Transactional(readOnly = true)
    public List<User> listActiveUsers() {
        return logAndReturnList("Listando usuários ativos",
                userRepository.findByActive(true), "Total de usuários ativos: {}");
    }

    @Transactional(readOnly = true)
    public List<User> listInactiveUsers() {
        return logAndReturnList("Listando usuários inativos",
                userRepository.findByActive(false), "Total de usuários inativos: {}");
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        log.info("Buscando usuário com ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("usernameNotFound")));
    }

    @Transactional
    public void updateUser(String email, UpdateUserRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userUpdateRequest.getName() != null && !userUpdateRequest.getName().isEmpty()) {
                user.setName(userUpdateRequest.getName());
            }
            if (userUpdateRequest.getCpf() != null && !userUpdateRequest.getCpf().isEmpty()) {
                user.setCpf(userUpdateRequest.getCpf());
            }
            if (userUpdateRequest.getPhoneNumber() != null && !userUpdateRequest.getPhoneNumber().isEmpty()) {
                user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            }
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public String deactivateUser(Long id) {
        User user = getUserAndUpdateLog(id, "Desativando usuário com ID: {}");
        user.setActive(false);
        userRepository.save(user);
        log.info("Usuário com ID: {} desativado com sucesso", id);
        return messageUtil.getMessage("deactivateUser");
    }

    @Transactional
    public String activateUser(Long id) {
        User user = getUserAndUpdateLog(id, "Ativando usuário com ID: {}");
        user.setActive(true);
        userRepository.save(user);
        log.info("Usuário com ID: {} ativado com sucesso", id);
        return messageUtil.getMessage("activateUser");
    }

    @Transactional
    public void deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

//    public String deleteUser(Long id) {
//        log.info("Excluindo usuário com ID: {}", id);
//        userRepository.deleteById(id);
//        log.info("Usuário com ID: {} excluído com sucesso", id);
//        return messageUtil.getMessage("deleteUser");
//    }

//    private void updateUserDetails(User user, UpdateUserRequest updatedUser) {
//        if (updatedUser.getName() != null) {
//            user.setName(updatedUser.getName());
//        }
//
//        if (updatedUser.getCpf() != null) {
//            user.setCpf(updatedUser.getCpf());
//        }
//
//        if (updatedUser.getPhoneNumber() != null) {
//            user.setPhoneNumber(updatedUser.getPhoneNumber());
//        }
//    }

    private User getUserAndUpdateLog(Long id, String message) {
        log.info(message, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("usernameNotFound")));
    }

    private User logAndReturnUser(String param, Optional<User> userOpt) {
        log.info("Buscando usuário pelo nome: {}", param);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
            return userOpt.get();
        }
        log.warn("Nenhum usuário encontrado com {}", param);
        return null;
    }

    private Optional<User> logAndReturnOptionalUser(String message, String param, Optional<User> userOpt) {
        log.info(message, param);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com {}", param);
        }
        return userOpt;
    }

    private List<User> logAndReturnList(String startMessage, List<User> users, String endMessage) {
        log.info(startMessage);
        List<User> userList = new ArrayList<>(users);
        log.info(endMessage, userList.size());
        return userList;
    }

    private List<User> logAndReturnList(String param, List<User> users) {
        log.info("Listando usuários pelo nome: {}", param);
        List<User> userList = new ArrayList<>(users);
        log.info("Total de usuários encontrados com o nome {}: {}", param, userList.size());
        return userList;
    }

    private List<User> logAndReturnList(boolean param, List<User> users) {
        log.info("Listando usuários com status ativo: {}", param);
        List<User> userList = new ArrayList<>(users);
        log.info("Total de usuários com status ativo {}: {}", param, userList.size());
        return userList;
    }

}
