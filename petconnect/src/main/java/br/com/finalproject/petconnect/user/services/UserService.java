package br.com.finalproject.petconnect.user.services;

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
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

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
                } else {
                    throw new IllegalArgumentException("As senhas não coincidem");
                }
            }

            User savedUser = userRepository.save(user);
            log.info("Usuário atualizado com sucesso: {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Falha ao atualizar usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao atualizar usuário");
        }
    }

    @Transactional
    public void deactivateUser(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

            // Atualizar o status do usuário para 'inactive'
            user.setActive(false);
            userRepository.save(user);

            log.info("Usuário desativado com sucesso: {}", email);
        } catch (Exception e) {
            log.error("Falha ao desativar usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao desativar usuário");
        }
    }

}
