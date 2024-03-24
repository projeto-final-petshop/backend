package br.com.projetofinal.petconnet.users.service;

import br.com.projetofinal.petconnet.exceptions.UnableToUpdateUserException;
import br.com.projetofinal.petconnet.exceptions.UserNotFoundException;
import br.com.projetofinal.petconnet.users.dto.UserRequest;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
import br.com.projetofinal.petconnet.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class UserHelper {
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserResponse saveUser(Users user) {
        try {
            user = userRepository.save(user);
            return UserMapper.userMapper().toUserResponse(user);
        } catch (UnableToUpdateUserException ex) {
            log.error("Erro ao tentar atualizar Usuário.");
            throw ex;
        }
    }

    public Users findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
    }

    public void updateUserData(UserRequest request, Users user) {
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
    }

}
