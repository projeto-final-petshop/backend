package br.com.finalproject.petconnect.services;

import br.com.finalproject.petconnect.exceptions.runtimes.conflict.CpfAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.domain.entities.Role;
import br.com.finalproject.petconnect.repositories.RoleRepository;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.domain.entities.User;
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

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registerUser(UserRequest input) {

        try {
            if (userRepository.existsByEmail(input.getEmail())) {
                log.error("Email {} já está cadastrado.", input.getEmail());
                throw new EmailAlreadyExistsException();
            }

            if (userRepository.existsByCpf(input.getCpf())) {
                log.error("CPF {} já está cadastrado.", input.getCpf());
                throw new CpfAlreadyExistsException();
            }

            Role role = roleRepository.findById(input.getRole())
                    .orElseThrow(() -> new FieldNotFoundException("Permissão"));

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

        } catch (ResourceNotFoundException | EmailAlreadyExistsException e) {
            log.error("Erro ao registrar usuário: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao registrar usuário: {}", e.getMessage());
            throw new ServiceException("Falha ao cadastrar o usuário. Dados inválidos ou erro na requisição.", e);
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
            throw new ServiceException("Falha ao listar Usuários. Por favor, tente mais tarde.", e);
        }
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, String email, String cpf, Boolean active, Pageable pageable) {
        try {
            Page<User> usersPage = userRepository.searchUsers(name, email, cpf, active, pageable);
            return usersPage.map(userMapper::toUserResponse);
        } catch (Exception e) {
            log.error("Falha ao buscar Usuários: {}", e.getMessage());
            throw new ServiceException("Falha ao buscar Usuários. Por favor, tente mais tarde.", e);
        }
    }

//    @Transactional(readOnly = true)
//    public Page<AppointmentResponse> listAllAppointments(Pageable pageable) {
//        try {
//            return appointmentRepository.findAll(pageable).map(appointmentMapper::toAppointmentResponse);
//        } catch (Exception e) {
//            log.error("Falha ao listar Agendamentos: {}", e.getMessage());
//            throw new DataModificationException("Falha ao listar Agendamentos. Por favor, tente mais tarde.", e);
//        }
//    }

    @Transactional(readOnly = true)
    public Page<PetResponse> listAllPets(Pageable pageable) {
        try {
            return petRepository.findAll(pageable).map(petMapper::toResponse);
        } catch (Exception e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new ServiceException("Falha ao listar Pets. Por favor, tente mais tarde.", e);
        }
    }

}