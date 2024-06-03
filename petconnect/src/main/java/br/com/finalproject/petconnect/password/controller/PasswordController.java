package br.com.finalproject.petconnect.password.controller;

import br.com.finalproject.petconnect.password.dto.PasswordResetRequest;
import br.com.finalproject.petconnect.password.dto.ResetPasswordRequest;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Password", description = "Atualização e Reset de Senha")
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "Atualizar senha")
    @ApiResponse(
            responseCode = "200", description = "Senha atualizada com sucesso!",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos. Verifique os campos e tente novamente.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.", content = @Content)
    @PutMapping("/users/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest passwordUpdateRequest) {

        passwordService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");

    }

    @Operation(summary = "Reset de senha")
    @ApiResponse(
            responseCode = "200", description = "O link de redefinição de senha foi enviado para seu e-mail.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = "E-mail não encontrado.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.", content = @Content)
    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        passwordService.resetPassword(request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("O link de redefinição de senha foi enviado para seu e-mail.");
    }

    @Operation(summary = "Confirmação de Reset de Senha")
    @ApiResponse(
            responseCode = "200", description = "Reset de senha realizado com sucesso!",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "Token de autenticação inválido.", content = @Content)
    @ApiResponse(responseCode = "400", description = "O token de autenticação expirou.", content = @Content)
    @ApiResponse(responseCode = "400", description = "As senhas não conferem.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.", content = @Content)
    @PostMapping("/auth/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(@RequestParam(name = "token") String token,
                                                       @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não conferem.");
        }
        passwordService.updatePasswordWithToken(token, resetPasswordRequest.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Reset de senha realizado com sucesso!");
    }

}
