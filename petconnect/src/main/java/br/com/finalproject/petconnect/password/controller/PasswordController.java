package br.com.finalproject.petconnect.password.controller;

import br.com.finalproject.petconnect.email.EmailService;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PasswordUpdateException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenExpiredException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenNotFoundException;
import br.com.finalproject.petconnect.password.dto.PasswordResetRequest;
import br.com.finalproject.petconnect.password.dto.ResetPasswordRequest;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.service.PasswordService;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class PasswordController {

    private final EmailService emailService;
    private final MessageUtil messageUtil;
    private final PasswordService passwordService;

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest passwordUpdateRequest) {

        try {
            passwordService.updatePassword(passwordUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(messageUtil.getMessage("updatePassword"));
        } catch (PasswordUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            passwordService.resetPassword(request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Password reset link has been sent to your email.");
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(@RequestParam("token") String token,
                                                       @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match.");
            }

            passwordService.updatePasswordWithToken(token, resetPasswordRequest.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Password successfully reset.");
        } catch (TokenNotFoundException | TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}