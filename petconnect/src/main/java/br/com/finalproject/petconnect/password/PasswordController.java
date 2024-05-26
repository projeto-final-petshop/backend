package br.com.finalproject.petconnect.password;

import br.com.finalproject.petconnect.exceptions.runtimes.PasswordUpdateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {

        try {
            passwordService.updatePassword(passwordUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
        } catch (PasswordUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
