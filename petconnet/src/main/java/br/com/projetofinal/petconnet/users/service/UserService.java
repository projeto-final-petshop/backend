package br.com.projetofinal.petconnet.users.service;

import br.com.projetofinal.petconnet.exceptions.errors.UnableToDeleteUserException;
import br.com.projetofinal.petconnet.users.dto.UserRequest;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
import br.com.projetofinal.petconnet.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserHelper userHelper;
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        Users users = UserMapper.userMapper().toEntity(request);
        users.setActive(Boolean.TRUE);
        return userHelper.saveUser(users);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getlAllUsers() {
        List<Users> users = userRepository.findAll();
        return UserMapper.userMapper().toUserListResponse(users);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        Users user = userHelper.findUserById(id);
        return UserMapper.userMapper().toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        Users user = userHelper.findUserById(id);
        userHelper.updateUserData(request, user);
        return userHelper.saveUser(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Users user = userHelper.findUserById(id);
        try {
            userRepository.delete(user);
        } catch (UnableToDeleteUserException ex) {
            log.error("Erro ao tentar excluir Usuário.");
            throw ex;
        }
    }

}
