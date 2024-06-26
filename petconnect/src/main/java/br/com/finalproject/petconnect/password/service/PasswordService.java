package br.com.finalproject.petconnect.password.service;

import br.com.finalproject.petconnect.email.EmailService;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.InvalidAuthenticationTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.EmailSendException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.PasswordUpdateException;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.entities.PasswordResetToken;
import br.com.finalproject.petconnect.password.repositories.PasswordResetTokenRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    @Transactional
    public void updatePassword(UpdatePasswordRequest passwordUpdateRequest) {
        User currentUser = getCurrentAuthenticatedUser();
        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), currentUser.getPassword())) {
            throw new PasswordUpdateException("alterar");
        }
        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
        currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Transactional
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("Email"));

        String token = UUID.randomUUID().toString();
        var passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8888/api/v1/reset-password/confirm?token=" + token;
        try {
            emailService.sendEmail(user.getEmail(),
                    "Solicitação de redefinição de senha.",
                    "Para redefinir sua senha, clique no link abaixo.\n" + resetLink);
        } catch (Exception e) {
            throw new EmailSendException();
        }
    }

    @Transactional
    public void updatePasswordWithToken(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new FieldNotFoundException("não encontrado"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidAuthenticationTokenException("inválido ou expirado");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);

        log.info("Senha atualizada com sucesso para o usuário: {}", user.getEmail());
    }

    @Transactional
    public void resetPasswordByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("Email"));
        sendPasswordResetEmail(user);
    }

    @Transactional
    public void resetPasswordByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new FieldNotFoundException("CPF"));
        sendPasswordResetEmail(user);
    }

    private void sendPasswordResetEmail(User user) {
        String token = UUID.randomUUID().toString();
        var passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8888/api/v1/auth/reset-password/confirm?token=" + token;
        try {
            emailService.sendEmail(user.getEmail(),
                    "Solicitação de redefinição de senha.",
                    "Para redefinir sua senha, clique no link abaixo:\n" + resetLink);
        } catch (Exception e) {
            throw new EmailSendException();
        }

        log.info("E-mail de redefinição de senha enviado para o usuário: {}", user.getEmail());
    }

    private User getCurrentAuthenticatedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new FieldNotFoundException("Email"));
    }

}
