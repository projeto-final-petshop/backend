package br.com.finalproject.petconnect.services.impl;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.domain.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final Validator validator;
    private final UserRepository userRepository;

    @Transactional
    public void updateUser(String email, UpdateUserRequest request) {

        validateUpdateUserRequest(request);
        final var user = emailNotFound(email);

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        userRepository.save(user);

    }

    @Transactional
    public void deactivateUser(String email) {
        final var user = emailNotFound(email);
        user.setActive(false);
        userRepository.save(user);
    }

    private User emailNotFound(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("Email" + email));
    }

    private void validateUpdateUserRequest(UpdateUserRequest request) throws InvalidRequestException {
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<UpdateUserRequest> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new InvalidRequestException(String.join(", ", errorMessages));
        }
    }

}
