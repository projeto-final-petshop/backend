package br.com.project.petconnect.app.user.service;

import br.com.project.petconnect.app.user.domain.dto.request.UpdatePasswordRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.core.exceptions.user.InvalidPasswordException;
import br.com.project.petconnect.core.exceptions.user.PasswordMismatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PasswordValidator {

    private final PasswordEncoder passwordEncoder;

    /**
     * Valida a senha atual, nova senha e confirmação de nova senha informadas para atualização de senha.
     *
     * @param request
     *         Dados para atualização de senha.
     * @param user
     *         Entidade de usuário.
     *
     * @throws InvalidPasswordException
     *         Caso a senha atual informada esteja incorreta.
     * @throws PasswordMismatchException
     *         Caso a nova senha e a confirmação de nova senha não sejam iguais.
     */
    public void validatePasswordUpdate(UpdatePasswordRequest request, UserEntity user)
            throws InvalidPasswordException, PasswordMismatchException {
        if (!isCurrentPasswordMatch(request.getCurrentPassword(), user.getPassword())) {
            log.error("Senha atual inválida!");
            throw new InvalidPasswordException();
        }
        if (!isConfirmPasswordMatch(request.getNewPassword(), request.getConfirmNewPassword())) {
            log.error("As novas senhas não conferem!");
            throw new PasswordMismatchException();
        }
        if (!isNewPasswordMatch(request.getNewPassword(), request.getConfirmNewPassword())) {
            log.error("As novas senhas não conferem!");
            throw new PasswordMismatchException();
        }
        if (!isPasswordValid(request.getNewPassword())) {
            log.error("A nova senha não atende aos critérios de segurança!");
            throw new InvalidPasswordException("A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
        }
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$");
    }

    private boolean isConfirmPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isNewPasswordMatch(String newPassword, String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }

    private boolean isCurrentPasswordMatch(String currentPassword, String hashedPasswordFromDB) {
        return passwordEncoder.matches(currentPassword, hashedPasswordFromDB);
    }

    /**
     * Valida se a nova senha e a confirmação de nova senha são iguais durante a criação de usuário.
     *
     * @param request
     *         Dados do usuário a ser criado.
     */
    public void validatePassword(UserRequest request) throws PasswordMismatchException {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
    }

}
