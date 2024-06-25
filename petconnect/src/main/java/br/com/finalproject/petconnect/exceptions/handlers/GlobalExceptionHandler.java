package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.AppointmentNotAvailableException;
import br.com.finalproject.petconnect.exceptions.InvalidAppointmentRequestException;
import br.com.finalproject.petconnect.exceptions.dto.ExceptionResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentDateException;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentTimeException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.*;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.CpfAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.FieldAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.NoEntityFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.UserNotRegisteredException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.InvalidTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.JwtTokenException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.PermissionDeniedException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.UserNotAuthenticatedException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        log.error("Erro de validação: {}", errors);

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Erro de validação.")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleGeneralExceptions(Exception ex) {
        List<String> errors = List.of(ex.getMessage());

        log.error("Erro interno do servidor: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Erro interno do servidor.")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class, InvalidAppointmentRequestException.class,
            InvalidCredentialsException.class, InvalidRequestException.class,
            JWTFieldExtractionFailureException.class, PasswordMismatchException.class,
            PastAppointmentDateException.class, PastAppointmentTimeException.class,
            UserInactiveException.class, JwtTokenException.class
    })
    public ResponseEntity<ExceptionResponse> handleCustomExceptions(RuntimeException ex) {
        log.error("Erro de requisição: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            UserNotAuthenticatedException.class, InvalidTokenException.class
    })
    public final ResponseEntity<ExceptionResponse> handleAuthenticatedException(RuntimeException ex) {
        log.error("Erro de autenticação: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            PermissionDeniedException.class
    })
    public final ResponseEntity<ExceptionResponse> handlePermissionDeniedException(RuntimeException ex) {
        log.error("Erro de permissão: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            FieldNotFoundException.class, ResourceNotFoundException.class,
            UserNotRegisteredException.class, NoEntityFoundException.class
    })
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        log.error("Recurso não encontrado: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CpfAlreadyExistsException.class, EmailAlreadyExistsException.class,
            FieldAlreadyExistsException.class, AppointmentNotAvailableException.class
    })
    public final ResponseEntity<ExceptionResponse> handleAlreadyExistsException(RuntimeException ex) {
        log.error("Conflito de dados: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            ServiceException.class, JWTServiceException.class, PasswordUpdateException.class,
            EmailSendException.class, OperationFailedException.class
    })
    public final ResponseEntity<ExceptionResponse> handleServiceException(RuntimeException ex) {
        log.error("Erro de serviço: {}", ex.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}