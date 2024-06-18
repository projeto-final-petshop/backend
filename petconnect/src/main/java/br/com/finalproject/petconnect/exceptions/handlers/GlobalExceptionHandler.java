package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.dto.ExceptionResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.DataModificationException;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.RequiredFieldException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordChangeException;
import br.com.finalproject.petconnect.exceptions.runtimes.password.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.InvalidServiceBookingException;
import br.com.finalproject.petconnect.exceptions.runtimes.store.InvalidStoreOperationException;
import br.com.finalproject.petconnect.exceptions.runtimes.user.InvalidUserDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.vet.InvalidVetAppointmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //    @ExceptionHandler({
//            Exception.class,
//            UserServiceException.class,
//            DataModificationException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
//        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//    }
//
//    @ExceptionHandler({
//            AuthenticationException.class,
//            InvalidTokenException.class,
//            PasswordResetTokenInvalidException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(RuntimeException ex) {
//        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
//    }
//
//    @ExceptionHandler({
//            AuthorizationException.class,
//            AccessDeniedException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleForbiddenException(AuthorizationException ex) {
//        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
//    }
//
//    @ExceptionHandler({
//            UserNotFoundException.class,
//            PetNotFoundException.class,
//            StoreNotFoundException.class,
//            AppointmentNotFoundException.class,
//            ServiceBookingNotFoundException.class,
//            EmailNotFoundException.class,
//            CpfNotFoundException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
//        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    @ExceptionHandler({
//            EmailAlreadyExistsException.class,
//            EmailSendException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleConflictException(RuntimeException ex) {
//        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
//    }
//
    @ExceptionHandler({
            PasswordMismatchException.class,
            InvalidStoreOperationException.class,
            InvalidVetAppointmentException.class,
            InvalidServiceBookingException.class,
            InvalidUserDataException.class,
            RequiredFieldException.class,
            PasswordChangeException.class
    })
    public ResponseEntity<ExceptionResponse> handleBadRequestsException(RuntimeException ex) {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Lógica de tratamento para exceções genéricas
        return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleBadRequestException(RuntimeException ex) {
        // Lógica de tratamento para exceções de runtime
        return new ResponseEntity<>("Requisição inválida", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataModificationException.class)
    public ResponseEntity<String> handleDataModificationException(DataModificationException ex) {
        // Lógica de tratamento específica para DataModificationException
        return new ResponseEntity<>("Erro na modificação de dados", HttpStatus.BAD_REQUEST);
    }

}
