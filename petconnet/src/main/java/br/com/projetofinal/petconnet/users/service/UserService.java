package br.com.projetofinal.petconnet.users.service;

import br.com.projetofinal.petconnet.users.dto.UserRequest;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
import br.com.projetofinal.petconnet.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserHelper userHelper;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        Users users = UserMapper.userMapper().toEntity(request);
        users.setActive(Boolean.TRUE);
        return userHelper.saveUser(users);
    }

}
