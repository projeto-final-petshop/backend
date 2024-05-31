package br.com.finalproject.petconnect.password.service;

import br.com.finalproject.petconnect.email.EmailService;
import br.com.finalproject.petconnect.exceptions.runtimes.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PasswordUpdateException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenExpiredException;
import br.com.finalproject.petconnect.exceptions.runtimes.TokenNotFoundException;
import br.com.finalproject.petconnect.password.dto.UpdatePasswordRequest;
import br.com.finalproject.petconnect.password.entities.PasswordResetToken;
import br.com.finalproject.petconnect.password.repositories.PasswordResetTokenRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    @Transactional
    public void updatePassword(UpdatePasswordRequest passwordUpdateRequest)
            throws PasswordUpdateException {

        User currentUser = getCurrentAuthenticatedUser();

        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), currentUser.getPassword())) {
            throw new PasswordUpdateException(INCORRECT_PASSWORD);
        }

        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
            throw new PasswordUpdateException(PASSWORDS_DO_NOT_MATCH);
        }

        currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(currentUser);

    }

    private User getCurrentAuthenticatedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public void resetPassword(String email) throws EmailNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(EMAIL_NOT_FOUND));

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8888/api/v1/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), PASSWORD_RESET_REQUEST, RESET_YOUR_PASSWORD + resetLink);
    }

    @Transactional
    public void updatePasswordWithToken(String token, String newPassword)
            throws TokenNotFoundException, TokenExpiredException {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(INVALID_TOKEN));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(TOKEN_EXPIRED);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);

    }

}
