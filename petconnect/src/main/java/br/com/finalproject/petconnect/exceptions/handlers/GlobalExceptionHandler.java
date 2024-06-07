package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.dto.ExceptionResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.auth.AuthenticationException;
import br.com.finalproject.petconnect.exceptions.runtimes.auth.AuthorizationException;
import br.com.finalproject.petconnect.exceptions.runtimes.auth.InvalidTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.cpf.CpfNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailSendException;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.AccessDeniedException;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.PetConnectServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.RequiredFieldException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordChangeException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordResetTokenInvalidException;
import br.com.finalproject.petconnect.exceptions.runtimes.pet.InvalidPetDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.pet.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.InvalidServiceBookingException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceBookingNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.store.InvalidStoreOperationException;
import br.com.finalproject.petconnect.exceptions.runtimes.store.StoreNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.*;
import br.com.finalproject.petconnect.exceptions.runtimes.vet.InvalidVetAppointmentException;
import br.com.finalproject.petconnect.exceptions.runtimes.vet.VetAppointmentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            UserServiceException.class,
            PetConnectServiceException.class
    })
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({
            AuthenticationException.class,
            InvalidTokenException.class,
            PasswordResetTokenInvalidException.class
    })
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({
            AuthorizationException.class,
            AccessDeniedException.class
    })
    public ResponseEntity<ExceptionResponse> handleForbiddenException(AuthorizationException ex) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            PetNotFoundException.class,
            StoreNotFoundException.class,
            VetAppointmentNotFoundException.class,
            ServiceBookingNotFoundException.class,
            EmailNotFoundException.class,
            CpfNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            EmailSendException.class
    })
    public ResponseEntity<ExceptionResponse> handleConflictException(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            PasswordMismatchException.class,
            InvalidPetDataException.class,
            InvalidStoreOperationException.class,
            InvalidVetAppointmentException.class,
            InvalidServiceBookingException.class,
            InvalidUserDataException.class,
            RequiredFieldException.class,
            PasswordChangeException.class
    })
    public ResponseEntity<ExceptionResponse> handleBadRequestException(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> buildResponseEntity(HttpStatus status, String message) {
        ExceptionResponse response = new ExceptionResponse(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(response);
    }

}
