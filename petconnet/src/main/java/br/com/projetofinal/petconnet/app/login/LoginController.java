package br.com.projetofinal.petconnet.app.login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if (loginService.login(request)) {
            return ResponseEntity.ok()
                    .body("Usuário logado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Não foi possível efetuar o Login, verifique seu acesso!");
        }
    }

}
