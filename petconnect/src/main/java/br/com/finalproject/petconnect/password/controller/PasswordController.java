package br.com.finalproject.petconnect.password.controller;

import br.com.finalproject.petconnect.exceptions.runtimes.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PasswordUpdateException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenExpiredException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenNotFoundException;
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

import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.*;

@Tag(name = "Password", description = "Atualização e Reset de Senha")
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "Atualizar senha")
    @ApiResponse(
            responseCode = "200", description = PASSWORD_UPDATED_SUCCESSFULLY,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = INVALID_INPUT_DATA, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @PutMapping("/users/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest passwordUpdateRequest) {

        try {
            passwordService.updatePassword(passwordUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(PASSWORD_UPDATED_SUCCESSFULLY);
        } catch (PasswordUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INCORRECT_PASSWORD + e.getMessage());
        }

    }

    @Operation(summary = "Reset de senha")
    @ApiResponse(
            responseCode = "200", description = RESET_PASSWORD_LINK_SENT,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = EMAIL_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        try {
            passwordService.resetPassword(request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(RESET_PASSWORD_LINK_SENT);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EMAIL_NOT_FOUND);
        }
    }

    @Operation(summary = "Confirmação de Reset de Senha")
    @ApiResponse(
            responseCode = "200", description = PASSWORD_RESET_SUCCESSFULLY,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = INVALID_AUTH_TOKEN, content = @Content)
    @ApiResponse(responseCode = "400", description = EXPIRED_TOKEN, content = @Content)
    @ApiResponse(responseCode = "400", description = PASSWORDS_DO_NOT_MATCH, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @PostMapping("/auth/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(@RequestParam(name = "token") String token,
                                                       @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        try {
            if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASSWORDS_DO_NOT_MATCH);
            }
            passwordService.updatePasswordWithToken(token, resetPasswordRequest.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body(PASSWORD_RESET_SUCCESSFULLY);
        } catch (TokenNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_AUTH_TOKEN + e.getMessage());
        } catch (TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EXPIRED_TOKEN + e.getMessage());
        }
    }

}
