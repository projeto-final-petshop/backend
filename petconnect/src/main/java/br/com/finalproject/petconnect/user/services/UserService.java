package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.RegisterUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> allUsers() {
        log.info("Buscando todos os usuários");
        List<User> users = new ArrayList<>(userRepository.findAll());
        log.info("Total de usuários encontrados: {}", users.size());
        return users;
    }

    public String updateUser(Long id, UpdateUserRequest updatedUser) {
        log.info("Atualizando usuário com ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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

        userRepository.save(user);
        log.info("Usuário com ID: {} atualizado com sucesso", id);
        return "Usuário atualizado com sucesso";
    }

    public String deactivateUser(Long id) {
        log.info("Desativando usuário com ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setActive(false);
        userRepository.save(user);
        log.info("Usuário com ID: {} desativado com sucesso", id);
        return "Usuário desativado com sucesso";
    }

    public String activateUser(Long id) {
        log.info("Ativando usuário com ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setActive(true);
        userRepository.save(user);
        log.info("Usuário com ID: {} ativado com sucesso", id);
        return "Usuário ativado com sucesso";
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
        log.info("Buscando usuário pelo nome: {}", name);
        User user = userRepository.findByName(name).stream().findFirst().orElse(null);
        if (user != null) {
            log.info("Usuário encontrado: {}", user.getId());
        } else {
            log.warn("Nenhum usuário encontrado com o nome: {}", name);
        }
        return user;
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("Buscando usuário pelo email: {}", email);
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com o email: {}", email);
        }
        return userOpt;
    }

    public Optional<User> findUserByCpf(String cpf) {
        log.info("Buscando usuário pelo CPF: {}", cpf);
        Optional<User> userOpt = userRepository.findByCpf(cpf);
        if (userOpt.isPresent()) {
            log.info("Usuário encontrado: {}", userOpt.get().getId());
        } else {
            log.warn("Nenhum usuário encontrado com o CPF: {}", cpf);
        }
        return userOpt;
    }

    public List<User> listUsersByName(String name) {
        log.info("Listando usuários pelo nome: {}", name);
        List<User> users = userRepository.findByName(name);
        log.info("Total de usuários encontrados com o nome {}: {}", name, users.size());
        return users;
    }

    public List<User> listAllUsers() {
        log.info("Listando todos os usuários");
        List<User> users = userRepository.findAll();
        log.info("Total de usuários cadastrados: {}", users.size());
        return users;
    }

    public List<User> findUsersByActive(boolean active) {
        log.info("Listando usuários com status ativo: {}", active);
        List<User> users = userRepository.findByActive(active);
        log.info("Total de usuários com status ativo {}: {}", active, users.size());
        return users;
    }

    public List<User> listActiveUsers() {
        log.info("Listando usuários ativos");
        List<User> users = userRepository.findByActive(true);
        log.info("Total de usuários ativos: {}", users.size());
        return users;
    }

    public List<User> listInactiveUsers() {
        log.info("Listando usuários inativos");
        List<User> users = userRepository.findByActive(false);
        log.info("Total de usuários inativos: {}", users.size());
        return users;
    }

    public String deleteUser(Long id) {
        log.info("Excluindo usuário com ID: {}", id);
        userRepository.deleteById(id.intValue());
        log.info("Usuário com ID: {} excluído com sucesso", id);
        return "Usuário excluído com sucesso";
    }

    public User getUserById(Long id) {
        log.info("Buscando usuário com ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

}
