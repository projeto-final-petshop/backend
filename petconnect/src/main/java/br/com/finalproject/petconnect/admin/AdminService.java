package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.PetConnectServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.role.RoleNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserAlreadyExistsException;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final PetMapper petMapper;
    private final UserMapper userMapper;
    private final AppointmentMapper appointmentMapper;

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AppointmentRepository appointmentRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registerUser(UserRequest input) {

        try {
            if (userRepository.existsByEmail(input.getEmail())) {
                log.error("Erro ao criar usuário: email {} já está cadastrado", input.getEmail());
                throw new UserAlreadyExistsException("exception.user.email_already_exists");
            }

            if (userRepository.existsByCpf(input.getCpf())) {
                log.error("Erro ao criar usuário: CPF {} já está cadastrado", input.getCpf());
                throw new UserAlreadyExistsException("exception.user.cpf_already_exists");
            }

            Role role = roleRepository.findById(input.getRole())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found"));

            User user = User.builder()
                    .name(input.getName())
                    .cpf(input.getCpf())
                    .email(input.getEmail())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .phoneNumber(input.getPhoneNumber())
                    .role(role)
                    .active(true)
                    .build();

            User savedUser = userRepository.save(user);

            log.info("Usuário registrado com sucesso: {}", input.getEmail());

            return UserMapper.INSTANCE.toUserResponse(savedUser);

        } catch (RoleNotFoundException | UserAlreadyExistsException e) {
            log.error("Erro ao registrar usuário: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao registrar usuário: {}", e.getMessage());
            throw new PetConnectServiceException("exception.user.registration_failed");
        }

    }

    @Transactional(readOnly = true)
    public Page<UserResponse> listAllUsers(Boolean active, Pageable pageable) {
        try {
            Page<User> usersPage = (active != null)
                    ? userRepository.findByActive(active, pageable)
                    : userRepository.findAll(pageable);

            return usersPage.map(userMapper::toUserResponse);
        } catch (Exception e) {
            log.error("Falha ao listar Usuários: {}", e.getMessage());
            throw new PetConnectServiceException("Falha ao listar Usuários. Por favor, tente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, String email, String cpf, Boolean active, Pageable pageable) {
        try {
            Page<User> usersPage = userRepository.searchUsers(name, email, cpf, active, pageable);
            return usersPage.map(userMapper::toUserResponse);
        } catch (Exception e) {
            log.error("Falha ao buscar Usuários: {}", e.getMessage());
            throw new PetConnectServiceException("Falha ao buscar Usuários. Por favor, tente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> listAllAppointments(Pageable pageable) {
        try {
            return appointmentRepository.findAll(pageable).map(appointmentMapper::toAppointmentResponse);
        } catch (Exception e) {
            log.error("Falha ao listar Agendamentos: {}", e.getMessage());
            throw new PetConnectServiceException("Falha ao listar Agendamentos. Por favor, tente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public Page<PetResponse> listAllPets(Pageable pageable) {
        try {
            return petRepository.findAll(pageable).map(petMapper::toResponse);
        } catch (Exception e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new PetConnectServiceException("Falha ao listar Pets. Por favor, tente mais tarde.");
        }
    }

}