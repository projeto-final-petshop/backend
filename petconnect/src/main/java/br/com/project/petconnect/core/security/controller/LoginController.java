package br.com.project.petconnect.core.security.controller;

import br.com.project.petconnect.app.user.domain.dto.response.UserResponse;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.app.user.mapping.UserMapper;
import br.com.project.petconnect.app.user.repository.UserRepository;
import br.com.project.petconnect.core.exceptions.user.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/details")
    public UserResponse getUserDetailsAfterLogin(Authentication authentication) {
        String email = authentication.getName();
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return UserMapper.userMapper().toUserResponse(userOptional.get());
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

    }

}
