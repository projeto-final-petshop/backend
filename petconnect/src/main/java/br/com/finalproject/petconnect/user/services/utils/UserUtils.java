package br.com.finalproject.petconnect.user.services.utils;

import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.InvalidUserDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.UserNotFoundException;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.finalproject.petconnect.user.services.constants.UserUtilsConstants.*;

@Slf4j
@Component
@AllArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public void checkEmailExists(String email) {
        if (email == null) {
            throw new InvalidUserDataException(EMAIL_NOT_NULL);
        }
        if (userRepository.findByEmail(email).isEmpty()) {
            String errorMessage = messageSource.getMessage(EMAIL_NOT_FOUND, new Object[]{email},
                    Locale.of(EMAIL_NOT_FOUND));
            throw new EmailNotFoundException(errorMessage);
        }
    }

    public void checkCPFExists(String cpf) {
        if (cpf == null) {
            throw new InvalidUserDataException(CPF_NOT_NULL);
        }
        if (userRepository.findByCpf(cpf).isEmpty()) {
            String errorMessage = messageSource.getMessage(CPF_NOT_FOUND, new Object[]{cpf},
                    Locale.of(CPF_NOT_FOUND));
            throw new UserNotFoundException(errorMessage);
        }
    }

    public void checkEmailNotExists(String email) {
        if (email == null) {
            throw new InvalidUserDataException(EMAIL_NOT_NULL);
        }
        if (userRepository.findByEmail(email).isPresent()) {
            String errorMessage = messageSource.getMessage(EMAIL_ALREADY_EXISTS, new Object[]{email},
                    Locale.of(EMAIL_ALREADY_EXISTS));
            throw new UserAlreadyExistsException(errorMessage);
        }
    }

    public void checkCPFNotExists(String cpf) {
        if (cpf == null) {
            throw new InvalidUserDataException(CPF_NOT_NULL);
        }
        if (userRepository.findByCpf(cpf).isPresent()) {
            String errorMessage = messageSource.getMessage(CPF_ALREADY_EXISTS, new Object[]{cpf},
                    Locale.of(CPF_ALREADY_EXISTS));
            throw new UserAlreadyExistsException(errorMessage);
        }
    }

    public void validateUserData(String name, String email, String cpf,
                                 String password, String confirmPassword) throws InvalidUserDataException {

        validateField(name, NAME_NOT_NULL);
        validateField(email, EMAIL_NOT_NULL);
        validateField(cpf, CPF_NOT_NULL);
        validateField(password, PASSWORD_NOT_NULL);

        if (!password.equals(confirmPassword)) {
            throw new InvalidUserDataException(messageSource.getMessage(PASSWORD_MISMATCH,
                    new Object[]{}, Locale.of(PASSWORD_MISMATCH)));
        }

        if (!isValidEmail(email)) {
            throw new InvalidUserDataException(messageSource.getMessage(INVALID_EMAIL,
                    new Object[]{}, Locale.of(INVALID_EMAIL_FORMAT)));
        }

        if (!isValidCPF(cpf)) {
            throw new InvalidUserDataException(messageSource.getMessage(INVALID_CPF,
                    new Object[]{}, Locale.of(INVALID_CPF_FORMAT)));
        }

    }

    private void validateField(String field, String errorCode) {
        if (field == null || field.isEmpty()) {
            throw new InvalidUserDataException(messageSource.getMessage(errorCode,
                    new Object[]{}, Locale.of(INVALID_FIELD)));
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidCPF(String cpf) {
        if (cpf == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(CPF_REGEX);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }

    public boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(
                        messageSource.getMessage(EMAIL_NOT_FOUND, new Object[]{email},
                                Locale.of(EMAIL_NOT_FOUND))));
    }

    public String getMessageByEmail(String email) {
        return messageSource.getMessage(EMAIL_NOT_FOUND,
                new Object[]{email},
                Locale.of(EMAIL_NOT_FOUND));
    }

}
