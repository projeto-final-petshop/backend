package br.com.finalproject.petconnect.password.controller;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.InvalidAuthenticationTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.EmailSendException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.PasswordUpdateException;
import br.com.finalproject.petconnect.password.dto.PasswordResetRequest;
import br.com.finalproject.petconnect.password.dto.ResetPasswordRequest;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.service.PasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping("/users/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest passwordUpdateRequest) {
        log.info("[ PasswordController - updatePassword ] - Iniciando atualização de senha para o usuário.");

        try {
            passwordService.updatePassword(passwordUpdateRequest);
            log.info("[ PasswordController - updatePassword ] - Senha atualizada com sucesso!");
            return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
        } catch (PasswordUpdateException e) {
            log.warn("Warn: Falha ao atualizar senha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (PasswordMismatchException e) {
            log.warn("Warn: Senhas não coincidem: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao atualizar senha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar senha.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        log.info("[ PasswordController - updatePassword ] - Recebida solicitação para redefinir senha do usuário com email: {}", request.getEmail());

        try {
            passwordService.resetPassword(request.getEmail());
            log.info("[ PasswordController - resetPassword ] - Link de redefinição de senha enviado com sucesso para o email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("O link de redefinição de senha foi enviado para seu e-mail.");
        } catch (FieldNotFoundException e) {
            log.warn("Warn: Email não encontrado ou não cadastrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailSendException e) {
            log.error("Erro ao enviar e-mail de redefinição de senha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar e-mail de redefinição de senha.");
        } catch (Exception e) {
            log.error("Erro ao resetar senha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao resetar senha.");
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(@RequestParam(name = "token") String token,
                                                       @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        log.info("[ PasswordController - confirmResetPassword ] - Recebida solicitação para confirmar reset de senha com token: {}", token);

        try {
            if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                log.error("Erro: As senhas não conferem.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não conferem.");
            }
            passwordService.updatePasswordWithToken(token, resetPasswordRequest.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Reset de senha realizado com sucesso!");
        } catch (FieldNotFoundException e) {
            log.warn("Warn: Token não encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidAuthenticationTokenException e) {
            log.warn("Warn: Token expirado ou inválido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao confirmar reset de senha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao confirmar reset de senha.");
        }
    }

}
