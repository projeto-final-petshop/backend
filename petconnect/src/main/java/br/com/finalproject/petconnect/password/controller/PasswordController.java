package br.com.finalproject.petconnect.password.controller;

import br.com.finalproject.petconnect.password.dto.PasswordResetRequest;
import br.com.finalproject.petconnect.password.dto.ResetPasswordRequest;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Password", description = "Operações relacionadas ao gerenciamento de senhas de usuários.")
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "Atualizar senha do usuário",
            description = "Atualiza a senha do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PutMapping("/users/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest passwordUpdateRequest) {
        log.info("Iniciando atualização de senha para o usuário.");
        passwordService.updatePassword(passwordUpdateRequest);
        log.info("Senha atualizada com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
    }

    @Operation(summary = "Redefinir senha do usuário",
            description = "Envia um e-mail com link para redefinição da senha do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail de redefinição de senha enviado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        log.info("Recebida solicitação para redefinir senha do usuário com email: {}", request.getEmail());
        passwordService.resetPassword(request.getEmail());
        log.info("Link de redefinição de senha enviado com sucesso para o email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("O link de redefinição de senha foi enviado para seu e-mail.");
    }

    @Operation(summary = "Confirmar redefinição de senha",
            description = "Confirma a redefinição de senha com base no token fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset de senha realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping("/auth/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(@RequestParam(name = "token") String token,
                                                       @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        log.info("Recebida solicitação para confirmar reset de senha com token: {}", token);
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            log.error("As senhas não conferem.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não conferem.");
        }
        passwordService.updatePasswordWithToken(token, resetPasswordRequest.getNewPassword());
        log.info("Reset de senha realizado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body("Reset de senha realizado com sucesso!");
    }

}
