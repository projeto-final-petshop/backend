package br.com.finalproject.petconnect.services.utils;

import br.com.finalproject.petconnect.domain.dto.request.LoginRequest;
import br.com.finalproject.petconnect.domain.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.exceptions.newexceptions.CpfAlreadyUsedException;
import br.com.finalproject.petconnect.exceptions.newexceptions.EmailAlreadyUsedException;
import br.com.finalproject.petconnect.exceptions.newexceptions.NewInvalidFieldException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class UserValidationService {

    private final Validator validator;
    private final UserRepository userRepository;

    public void validateUserRequest(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException("Email já em uso");
        }

        if (userRepository.existsByCpf(request.getCpf())) {
            throw new CpfAlreadyUsedException("CPF já em uso");
        }

        if (request.getName().length() < 3) {
            throw new NewInvalidFieldException("O nome deve ter no mínimo 3 caracteres");
        }

        if (!isValidCPF(request.getCpf())) {
            throw new NewInvalidFieldException("CPF inválido");
        }

        if (!isValidEmail(request.getEmail())) {
            throw new NewInvalidFieldException("Email inválido");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new NewInvalidFieldException("A senha não atende aos critérios de segurança");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new NewInvalidFieldException("Senhas não coincidem");
        }

        if (request.getPhoneNumber() != null && !isValidPhoneNumber(request.getPhoneNumber())) {
            throw new NewInvalidFieldException("Número de telefone inválido");
        }

    }

    public void validateUpdateUserRequest(UpdateUserRequest updateRequest, User user) {
        if (updateRequest.getName() != null && updateRequest.getName().length() >= 3) {
            user.setName(updateRequest.getName());
        }

        if (updateRequest.getPhoneNumber() != null && isValidPhoneNumber(updateRequest.getPhoneNumber())) {
            user.setPhoneNumber(updateRequest.getPhoneNumber());
        }

        if (updateRequest.getAddress() != null) {
            user.setAddress(updateRequest.getAddress());
        }
    }

    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
                && !password.matches(".*(abc|ABC|123).*");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\+?\\d{9,14}$");
    }

    public void validateInputUser(UserRequest input) {

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UserRequest> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new InvalidRequestException(String.join(", ", errorMessages));
        }

        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

    }

    public void validateInputLogin(LoginRequest input) {

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<LoginRequest> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new InvalidRequestException(String.join(", ", errorMessages));
        }

    }

}
