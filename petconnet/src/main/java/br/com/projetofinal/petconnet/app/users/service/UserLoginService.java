package br.com.projetofinal.petconnet.app.users.service;

import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public User geByUsername(String username) {
        log.info("[ User Service - getUserByUsername ] --- Buscando usuário com username: {}", username);
        User user = userRepository.findByUsername(username).get();
        return user;
    }

}
