package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserServiceException;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.constants.ConstantsUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    /**
     * Atualizar Usuário
     */
    @Transactional
    public void updateUser(String email, UpdateUserRequest updateUserRequest) {
        try {
            final var user = getUser(email);
            validateAndUpdateFields(updateUserRequest, user);
            User savedUser = userRepository.save(user);
            log.info("Usuário atualizado com sucesso: {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Falha ao atualizar usuário: {}", e.getMessage());
            throw new UserServiceException(ConstantsUtil.FAILED_TO_UPDATE_USER);
        }
    }

    private void validateAndUpdateFields(UpdateUserRequest updateUserRequest, User user) {
        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if (updateUserRequest.getAddress() != null) {
            user.setAddress(updateUserRequest.getAddress());
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
            throw new UserServiceException(ConstantsUtil.FAILED_TO_DEACTIVE_USER);
        }
    }

    @Transactional
    public void deleteUser(String email) {
        try {
            final var user = getUser(email);
            deletePetsByUser(user);
            userRepository.delete(user);
            log.info("Usuário deletado com sucesso: {}", email);
        } catch (Exception e) {
            log.error("Falha ao deletar usuário: {}", e.getMessage());
            throw new UserServiceException(ConstantsUtil.FAILED_TO_DELETE_USER);
        }
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ConstantsUtil.USER_NOT_FOUND));
    }

    private void deletePetsByUser(User user) {
        petRepository.deleteByUserId(user.getId());
    }

}
