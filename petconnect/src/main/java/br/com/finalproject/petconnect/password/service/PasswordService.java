package br.com.finalproject.petconnect.password.service;

import br.com.finalproject.petconnect.email.EmailService;
import br.com.finalproject.petconnect.exceptions.runtimes.auth.InvalidTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.cpf.CpfNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordChangeException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordResetTokenInvalidException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.entities.PasswordResetToken;
import br.com.finalproject.petconnect.password.repositories.PasswordResetTokenRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.constants.ConstantsUtil;
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
        try {
            log.info("Iniciando atualização de senha.");
            User currentUser = getCurrentAuthenticatedUser();
            if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), currentUser.getPassword())) {
                throw new PasswordChangeException(ConstantsUtil.ERROR_CHANGING_PASSWORD);
            }
            if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
                throw new PasswordChangeException(ConstantsUtil.PASSWRODS_DO_NOT_MATCH);
            }
            currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
            userRepository.save(currentUser);
            log.info("Senha atualizada com sucesso para o usuário: {}", currentUser.getEmail());
        } catch (PasswordChangeException e) {
            log.error("Erro ao atualizar senha: {}", e.getMessage());
            throw new PasswordChangeException(ConstantsUtil.ERROR_UPDATING_PASSWORD + e.getMessage());
        }
    }

    private User getCurrentAuthenticatedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException(ConstantsUtil.USER_NOT_FOUND));
    }

    @Transactional
    public void resetPassword(String email) {
        try {
            log.info("Iniciando redefinição de senha para o email: {}", email);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EmailNotFoundException(ConstantsUtil.EMAIL_NOT_FOUND));
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
            tokenRepository.save(passwordResetToken);
            String resetLink = "http://localhost:8888/api/v1/reset-password/confirm?token=" + token;
            emailService.sendEmail(user.getEmail(),
                    "Solicitação de redefinição de senha.",
                    "Para redefinir sua senha, clique no link abaixo.\n" + resetLink);
            log.info("E-mail de redefinição de senha enviado para o usuário: {}", user.getEmail());
        } catch (PasswordResetTokenInvalidException e) {
            log.error("Erro ao redefinir senha: {}", e.getMessage());
            throw new PasswordResetTokenInvalidException(ConstantsUtil.PASSWORD_RESET_TOKEN_INVALID + e.getMessage());
        }
    }

    @Transactional
    public void updatePasswordWithToken(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException(ConstantsUtil.INVALID_TOKEN));
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException(ConstantsUtil.INVALID_TOKEN);
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
                .orElseThrow(() -> new EmailNotFoundException(ConstantsUtil.EMAIL_NOT_FOUND));
        sendPasswordResetEmail(user);
    }

    @Transactional
    public void resetPasswordByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new CpfNotFoundException(ConstantsUtil.CPF_NOT_FOUND));
        sendPasswordResetEmail(user);
    }

    private void sendPasswordResetEmail(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);
        String resetLink = "http://localhost:8888/api/v1/auth/reset-password/confirm?token=" + token;
        emailService.sendEmail(user.getEmail(),
                "Solicitação de redefinição de senha.",
                "Para redefinir sua senha, clique no link abaixo:\n" + resetLink);
        log.info("E-mail de redefinição de senha enviado para o usuário: {}", user.getEmail());
    }

}
