package br.com.projetofinal.petconnet.users.service;

import br.com.projetofinal.petconnet.exceptions.errors.users.*;
import br.com.projetofinal.petconnet.users.dto.UserRequest;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
import br.com.projetofinal.petconnet.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.info("Email já cadastrado");
            throw new EmailAlreadyRegisteredException();
        }
        Users user = UserMapper.userMapper().toEntity(request);
        user.setActive(Boolean.TRUE);
        user = userRepository.save(user);
        return UserMapper.userMapper().toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getlAllUsers() {
        List<Users> users = userRepository.findAll();
        return UserMapper.userMapper().toUserListResponse(users);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        Users user = findUserById(id);
        return UserMapper.userMapper().toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        Users user = findUserById(id);
        updateUserData(request, user);
        return UserMapper.userMapper().toUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Users user = findUserById(id);
        try {
            userRepository.delete(user);
        } catch (UnableToDeleteUserException ex) {
            log.error("Erro ao tentar excluir Usuário.");
            throw ex;
        }
    }

    private Users findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private void updateUserData(UserRequest request, Users user) {
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
    }

}
