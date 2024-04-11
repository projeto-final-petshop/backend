package br.com.projetofinal.petconnet.app.login;

import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public boolean login(LoginRequest request) {
        // TODO: implementar a lógica verificar se o username existe.
        // TODO: se o usuário não exister ou estiver incorreto deve retornar uma exception
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()) != null;
    }

}
