package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<User> allUsers() {
        return logAndReturnList("Buscando todos os usuários",
                userRepository.findAll(), "Total de usuários encontrados: {}");
    }

    public User findUser(FindUserRequest request) {
        if (request.getName() != null) {
            return findUserByName(request.getName());
        }

        if (request.getEmail() != null) {
            return findUserByEmail(request.getEmail()).orElse(null);
        }

        if (request.getCpf() != null) {
            return findUserByCpf(request.getCpf()).orElse(null);
        }

        if (!request.isActive()) {
            List<User> users = findUsersByActive(false);
            return users.isEmpty() ? null : users.getFirst();
        }

        return null;
    }

    public User findUserByName(String name) {
        return logAndReturnUser(
                name, userRepository.findByName(name).stream().findFirst());
    }

    public Optional<User> findUserByEmail(String email) {
        return logAndReturnOptionalUser("Buscando usuário pelo email: {}",
                email, userRepository.findByEmail(email));
    }

    public Optional<User> findUserByCpf(String cpf) {
        return logAndReturnOptionalUser("Buscando usuário pelo CPF: {}",
                cpf, userRepository.findByCpf(cpf));
    }

    public List<User> listUsersByName(String name) {
        return logAndReturnList(
                name, userRepository.findByName(name));
    }

    public List<User> findUsersByActive(boolean active) {
        return logAndReturnList(
                active, userRepository.findByActive(active));
    }

    public List<User> listActiveUsers() {
        return logAndReturnList("Listando usuários ativos",
                userRepository.findByActive(true), "Total de usuários ativos: {}");
    }

    public List<User> listInactiveUsers() {
        return logAndReturnList("Listando usuários inativos",
                userRepository.findByActive(false), "Total de usuários inativos: {}");
    }

    public User getUserById(Long id) {
        log.info("Buscando usuário com ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageUtil.getMessage("usernameNotFound")));
    }

    @Transactional
    public String updateUser(Long id, UpdateUserRequest updatedUser) {
        User user = getUserAndUpdateLog(id, "Atualizando usuário com ID: {}");
        updateUserDetails(user, updatedUser);
        userRepository.save(user);
        log.info("Usuário com ID: {} atualizado com sucesso", id);
        return "Usuário atualizado com sucesso";
    }

    @Transactional
    public String deactivateUser(Long id) {
        User user = getUserAndUpdateLog(id, "Desativando usuário com ID: {}");
        user.setActive(false);
        userRepository.save(user);
        log.info("Usuário com ID: {} desativado com sucesso", id);
        return "Usuário desativado com sucesso";
    }

    @Transactional
    public String activateUser(Long id) {
        User user = getUserAndUpdateLog(id, "Ativando usuário com ID: {}");
        user.setActive(true);
        userRepository.save(user);
        log.info("Usuário com ID: {} ativado com sucesso", id);
        return "Usuário ativado com sucesso";
    }

    @Transactional
    public String deleteUser(Long id) {
        log.info("Excluindo usuário com ID: {}", id);
        userRepository.deleteById(id);
        log.info("Usuário com ID: {} excluído com sucesso", id);
        return "Usuário excluído com sucesso";
    }

    private void updateUserDetails(User user, UpdateUserRequest updatedUser) {
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getCpf() != null) {
            user.setCpf(updatedUser.getCpf());
        }

        if (updatedUser.getPhoneNumber() != null) {
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
    }

    private User getUserAndUpdateLog(Long id, String message) {
        log.info(message, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageUtil.getMessage("usernameNotFound")));
    }

    private User logAndReturnUser(String param, Optional<User> userOpt) {
        log.info("Buscando usuário pelo nome: {}", param);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
            return userOpt.get();
        } else {
            log.warn("Nenhum usuário encontrado com {}", param);
            return null;
        }
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
