package br.com.projetofinal.petconnet.users.service;

import br.com.projetofinal.petconnet.exceptions.UnableToUpdateUserException;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
import br.com.projetofinal.petconnet.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserHelper {

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

}
