package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.EmailSendException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.JWTServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.PasswordUpdateException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.user.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUser(String email, UpdateUserRequest request) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + email));

            if (request.getName() != null) {
                user.setName(request.getName());
            }

            if (request.getPhoneNumber() != null) {
                user.setPhoneNumber(request.getPhoneNumber());
            }

            if (request.getAddress() != null) {
                user.setAddress(request.getAddress());
            }

            userRepository.save(user);

            log.info("Usuário atualizado com sucesso: {}", user.getEmail());
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário", e);
            handleServiceException(e);
        }
    }

    @Transactional
    public void deactivateUser(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + email));

            user.setActive(false);
            userRepository.save(user);

            log.info("Usuário desativado com sucesso: {}", user.getEmail());
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (Exception e) {
            log.error("Erro ao desativar usuário", e);
            handleServiceException(e);
        }
    }

    public List<User> allUsers() {
        try {
            return new ArrayList<>(userRepository.findAll());
        } catch (Exception e) {
            log.error("Erro ao buscar todos os usuários", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar todos os usuários", e);
        }
    }

    private void handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("Recurso não encontrado: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    private void handleServiceException(Exception e) {
        switch (e.getClass().getSimpleName()) {
            case "ServiceException" -> {
                log.error("Erro interno de serviço: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno de serviço", e);
            }
            case "JWTServiceException" -> {
                log.error("Erro no serviço JWT: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no serviço JWT", e);
            }
            case "PasswordUpdateException" -> {
                log.error("Erro na atualização de senha: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na atualização de senha", e);
            }
            case "EmailSendException" -> {
                log.error("Erro no envio de e-mail: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no envio de e-mail", e);
            }
            default -> {
                log.error("Erro desconhecido no serviço.", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro desconhecido no serviço", e);
            }
        }
    }

}
