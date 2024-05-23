package br.com.project.petconnect.user.service;

import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.dto.UserResponse;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.mapping.UserMapper;
import br.com.project.petconnect.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserHelper {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserHelper(UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateUsernameExists(String username) {
        if (usernameExists(username)) {
            log.warn("User Service --- Usuário com o email {} já existe.", username);
            throw new UsernameNotFoundException("Usuário com email já cadastrado");
        }
    }

    public void validateDocumentNumberExists(String documentNumber) {
        if (documentNumberExists(documentNumber)) {
            log.warn("User Service --- Usuário com o email {} já existe.", documentNumber);
            throw new UsernameNotFoundException("Usuário com CPF já cadastrado");
        }
    }

    public User createUser(UserRequest request) throws Exception {
        User user = UserMapper.userMapper().toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return saveUser(user);
    }

    public UserResponse toRegisterUserResponse(User savedUser) {
        return UserMapper.userMapper().toResponse(savedUser);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    private boolean documentNumberExists(String documentNumber) {
        return userRepository.findByCpf(documentNumber).isPresent();
    }

    private User saveUser(User user) throws Exception {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("User Service --- Erro ao cadastrar usuário: {}", e.getMessage());
            throw new Exception();
        }
    }

    public User findUserById(Long id) {
        log.info("User Service --- Buscando usuário com ID {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User Service --- Usuário com o ID {} não encontrado.", id);
                    return new UsernameNotFoundException("Usuário naõ encontrado");
                });
    }

    public UserResponse toUserResponse(User user) {
        return UserMapper.userMapper().toResponse(user);
    }

    public List<UserResponse> toUserResponseList(List<User> users) {
        return UserMapper.userMapper().toResponseList(users);
    }

    public User findAndValidateUser(Long id) {
        User user = findUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

    public void updateUserFields(User user, UserRequest request) {
        if (!user.getUsername().equals(request.getEmail())) {
            validateUsernameExists(request.getEmail());
            user.setEmail(request.getEmail());
        }
        user.setName(request.getName());

    }

    public User findUserByUsername(String username) {
        log.info("User Service --- Buscando usuário com username {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("User Service --- Usuário com o username {} não encontrado.", username);
                    return new UsernameNotFoundException("Usuário com o email não encontrado");
                });
    }

    public UserResponse handleUserUpdate(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.userMapper().toResponse(savedUser);
    }

    public User findUserAndHandleNotFound(Long id) {
        try {
            return findUserById(id);
        } catch (UsernameNotFoundException e) {
            log.error("User Service --- Usuário não encontrado com ID: {}", id, e);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

}

