package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.NOT_FOUND_USER_MESSAGE;

@Slf4j
@Component
@AllArgsConstructor
public class UserServiceUtils {

    private final UserRepository userRepository;

    public void updateUserFields(User user, UserRequest userRequest) {
        updateName(user, userRequest);
        updateEmail(user, userRequest);
        updateCpf(user, userRequest);
        updatePhoneNumber(user, userRequest);
    }

    public void updatePhoneNumber(User user, UserRequest userRequest) {
        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
            log.info("Telefone do usuário atualizado para: {}", userRequest.getPhoneNumber());
        }
    }

    public void updateCpf(User user, UserRequest userRequest) {
        if (userRequest.getCpf() != null && !userRequest.getCpf().isEmpty()) {
            user.setCpf(userRequest.getCpf());
            log.info("CPF do usuário atualizado para: {}", userRequest.getCpf());
        }
    }

    public void updateEmail(User user, UserRequest userRequest) {
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
            log.info("Email do usuário atualizado para: {}", userRequest.getEmail());
        }
    }

    public void updateName(User user, UserRequest userRequest) {
        if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
            user.setName(userRequest.getName());
            log.info("Nome do usuário atualizado para: {}", userRequest.getName());
        }
    }

    public User findFirstInactiveUserOrThrowException() {
        List<User> inactiveUsers = findUsersByActive(false);
        if (!inactiveUsers.isEmpty()) {
            User firstInactiveUser = inactiveUsers.getFirst();
            log.info("Primeiro usuário inativo encontrado: {}", firstInactiveUser.getId());
            return firstInactiveUser;
        }
        throw new UserNotFoundException("Nenhum usuário inativo encontrado");
    }

    public User findUserByEmailOrThrowException(String email) {
        return findUserByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Email não encontrado: {}", email);
                    return new EmailNotFoundException("Email não encontrado: " + email);
                });
    }

    public User findUserByCpfOrThrowException(String cpf) {
        return findUserByCpf(cpf)
                .orElseThrow(() -> {
                    log.warn("CPF não encontrado: {}", cpf);
                    return new UserNotFoundException("CPF não encontrado: " + cpf);
                });
    }

    public User findUserByName(String name) {
        log.info("Buscando usuário pelo nome: {}", name);
        Optional<User> userOptional = userRepository.findByName(name).stream().findFirst();
        if (userOptional.isPresent()) {
            log.info("Usuário encontrado: {}", userOptional.get().getId());
            return userOptional.get();
        }
        log.warn("Nenhum usuário encontrado com {}", name);
        return null;
    }

    public List<User> findUsersByActive(boolean active) {
        log.info("Listando usuários com status ativo: {}", active);
        List<User> users = userRepository.findByActive(active);
        log.info("Total de usuários com status ativo {}: {}", active, users.size());
        return users;
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("Buscando usuário pelo email: {}", email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> log.info("Usuário encontrado: {}", user.getId()));
        log.warn("Nenhum usuário encontrado com {}", email);
        return userOptional;
    }

    public Optional<User> findUserByCpf(String cpf) {
        log.info("Buscando usuário pelo CPF: {}", cpf);
        Optional<User> userOptional = userRepository.findByCpf(cpf);
        userOptional.ifPresent(user -> log.info("Usuário encontrado: {}", user.getId()));
        log.warn("Nenhum usuário encontrado com {}", cpf);
        return userOptional;
    }

}
