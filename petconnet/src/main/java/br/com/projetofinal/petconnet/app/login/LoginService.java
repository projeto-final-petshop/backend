package br.com.projetofinal.petconnet.app.login;

import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public boolean login(LoginRequest request) {
        System.out.println("Login request: " + request.getUsername());
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()) != null;
    }

}
