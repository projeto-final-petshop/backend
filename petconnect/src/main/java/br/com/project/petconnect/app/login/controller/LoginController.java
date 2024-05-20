package br.com.project.petconnect.app.login.controller;

import br.com.project.petconnect.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

//    @GetMapping("/login")
//    public UserEntity getUserDetailsAfterLogin(Authentication authentication) {
//        Optional<UserEntity> customers = userRepository.findByEmail(authentication.getName());
//        if (customers.size() > 0) {
//            return customers.get(0);
//        } else {
//            return null;
//        }
//
//    }

}
