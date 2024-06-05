package br.com.finalproject.petconnect.user.services;

import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.UserServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.InvalidUserDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserServiceUtils userServiceUtils;
    private final PetRepository petRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

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
    public void deleteUser(String email) {
        try {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

            // Deletar registros dependentes
            // Primeiro, deletar os appointments que referenciam os pets do usuário
            List<Pet> pets = petRepository.findByUserId(user.getId());
            for (Pet pet : pets) {
                appointmentRepository.deleteByPetId(pet.getId());
            }

            // Depois, deletar os pets do usuário
            petRepository.deleteByUserId(user.getId());

            // Finalmente, deletar o usuário
            userRepository.delete(user);
            log.info("Usuário excluído com sucesso: {}", email);
        } catch (Exception e) {
            log.error("Falha ao excluir usuário: {}", e.getMessage());
            throw new UserServiceException("Falha ao excluir usuário");
        }
    }

    @Transactional
    public UserResponse findUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
            return userMapper.toUserResponse(user);
        } catch (Exception e) {
            log.error("Falha ao buscar usuário por email: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar usuário por email");
        }
    }

    @Transactional
    public UserResponse findUserByCpf(String cpf) {
        try {
            User user = userRepository.findByCpf(cpf)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
            return userMapper.toUserResponse(user);
        } catch (Exception e) {
            log.error("Falha ao buscar usuário por CPF: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar usuário por CPF");
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.toUserResponseList(users);
        } catch (Exception e) {
            log.error("Falha ao buscar todos os usuários: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar todos os usuários");
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findActiveUsers() {
        try {
            List<User> users = userRepository.findByActiveTrue();
            return userMapper.toUserResponseList(users);
        } catch (Exception e) {
            log.error("Falha ao buscar usuários ativos: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar usuários ativos");
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findInactiveUsers() {
        try {
            List<User> users = userRepository.findByActiveFalse();
            return userMapper.toUserResponseList(users);
        } catch (Exception e) {
            log.error("Falha ao buscar usuários inativos: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar usuários inativos");
        }
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, String email, String cpf, Boolean active, Pageable pageable) {
        try {
            Page<User> usersPage = userRepository
                    .findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndCpfContainingIgnoreCaseAndActive(
                            name, email, cpf, active, pageable);
            return usersPage.map(userMapper::toUserResponse);
        } catch (Exception e) {
            log.error("Falha ao buscar usuários: {}", e.getMessage());
            throw new UserServiceException("Falha ao buscar usuários");
        }
    }

    @Transactional(readOnly = true)
    public User findUser(FindUserRequest request) {
        try {
            if (request.getName() != null) {
                return userServiceUtils.findUserByName(request.getName());
            }
            if (request.getEmail() != null) {
                return userServiceUtils.findUserByEmailOrThrowException(request.getEmail());
            }
            if (request.getCpf() != null) {
                return userServiceUtils.findUserByCpfOrThrowException(request.getCpf());
            }
            if (!request.getActive()) {
                return userServiceUtils.findFirstInactiveUserOrThrowException();
            }
            throw new InvalidUserDataException("NO_SEARCH_CRITERIA_PROVIDED_MESSAGE");
        } catch (Exception e) {
            log.error("Erro ao buscar usuário: {}", e.getMessage());
            throw new UserServiceException("Erro ao buscar usuário");
        }
    }

    @Transactional(readOnly = true)
    public List<User> listUsersByName(String name) {
        try {
            return userRepository.findByName(name);
        } catch (Exception e) {
            log.error("Erro ao listar usuários pelo nome: {}", e.getMessage());
            throw new UserServiceException("Erro ao listar usuários pelo nome");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
        } catch (Exception e) {
            log.error("Erro ao buscar usuário pelo ID: {}", e.getMessage());
            throw new UserServiceException("Erro ao buscar usuário pelo ID");
        }
    }

}
