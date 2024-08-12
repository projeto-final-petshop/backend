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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    @Transactional
    public void updatePassword(UpdatePasswordRequest passwordUpdateRequest) {

        User currentUser = getCurrentAuthenticatedUser();

        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), currentUser.getPassword())) {
            log.warn("Warn: Tentativa falha de alterar senha para o usuário: {}", currentUser.getEmail());
            throw new PasswordUpdateException("Senha atual incorreta.");
        }

        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
            log.warn("Warn: Senha nova e confirmação não coincidem para o usuário: {}", currentUser.getEmail());
            throw new PasswordMismatchException("Nova senha e confirmação não coincidem.");
        }
        
        currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(currentUser);

        log.info("[ PasswordService - updatePassword ] - Senha alterada com sucesso para o usuário: {}", currentUser.getEmail());

    }

    @Transactional
    public void resetPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("[ PasswordService - resetPassword ] - Email não encontrado ou não cadastrado: {}", email);
                    return new FieldNotFoundException("Email não encontrado ou não cadastrado.");
                });

        String token = UUID.randomUUID().toString();
        var passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        try {
            emailService.sendEmail(user.getEmail(),
                    "Solicitação de redefinição de senha.",
                    "Para redefinir sua senha, clique no link abaixo.\n" + resetLink);
            log.info("[ PasswordService - resetPassword ] - E-mail de redefinição de senha enviado para: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de redefinição de senha para: {}", user.getEmail(), e);
            throw new EmailSendException("Erro ao enviar e-mail de redefinição de senha.");
        }

    }

    @Transactional
    public void updatePasswordWithToken(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Warn: Token não encontrado: {}", token);
                    return new FieldNotFoundException("Token não encontrado.");
                });

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Warn: Token expirado para redefinição de senha do usuário: {}", resetToken.getUser().getEmail());
            throw new InvalidAuthenticationTokenException("Token de autenticação inválido ou expirado.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);

        log.info("[ PasswordService - updatePasswordWithToken ] - Senha atualizada com sucesso para o usuário: {}", user.getEmail());

    }

    private User getCurrentAuthenticatedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new FieldNotFoundException("Email não encontrado ou não cadastrado."));
    }

}
