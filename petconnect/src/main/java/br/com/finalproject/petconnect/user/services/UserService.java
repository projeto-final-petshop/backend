package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserServiceException;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Atualizar Usuário
     */
    @Transactional
    public void updateUser(String email, UserRequest userRequest) {
        try {
            final var user = getUser(email);
            validateField(userRequest, user);
            User savedUser = userRepository.save(user);
            log.info("Usuário atualizado com sucesso: {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Falha ao atualizar usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao atualizar usuário");
        }
    }

    private void validateField(UserRequest userRequest, User user) {
        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }
        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (userRequest.getAddress() != null) {
            user.setAddress(userRequest.getAddress());
        }
        if (userRequest.getPassword() != null && userRequest.getConfirmPassword() != null) {
            if (userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            throw new PasswordMismatchException("As senhas não coincidem");
        }
    }

    @Transactional
    public void deactivateUser(String email) {
        try {
            final var user = getUser(email);
            user.setActive(false);
            userRepository.save(user);
            log.info("Usuário desativado com sucesso: {}", email);
        } catch (Exception e) {
            log.error("Falha ao desativar usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao desativar usuário");
        }
    }

    @Transactional
    public void deleteUser(String email) {
        try {
            final var user = getUser(email);
            userRepository.delete(user);
            log.info("Usuário deletado com sucesso: {}", email);
        } catch (Exception e) {
            log.error("Falha ao deletar usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao deletar usuário");
        }
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

}
