package br.com.finalproject.petconnect.exceptions.handlers;

import br.com.finalproject.petconnect.exceptions.dto.ExceptionResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.*;
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
            InvalidRequestException.class,
            DataIntegrityViolationException.class,
            PasswordsDoNotMatchException.class,
            JWTFieldExtractionFailureException.class,
            UserInactiveException.class,
            InvalidCredentialsException.class
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
            UserNotAuthenticatedException.class,
            InvalidAuthenticationTokenException.class
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
            ResourceNotFoundException.class,
            FieldNotFoundException.class,
            UserNotRegisteredException.class
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
            CpfAlreadyExistsException.class,
            EmailAlreadyExistsException.class,
            FieldAlreadyExistsException.class
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
            ServiceException.class,
            JWTServiceException.class
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

//    @ExceptionHandler({
//            InvalidRequestException.class,
//            DataIntegrityViolationException.class,
//            PasswordsDoNotMatchException.class,
//            JWTFieldExtractionFailureException.class,
//            UserInactiveException.class,
//            InvalidCredentialsException.class
//    })
//    public ResponseEntity<ExceptionResponse> handleCustomExceptions(RuntimeException ex) {
//        log.error("Erro de requisição: {}", ex.getMessage());
//        var response = ExceptionResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({
//            UserNotAuthenticatedException.class,
//            InvalidAuthenticationTokenException.class
//    })
//    public final ResponseEntity<ExceptionResponse> handleAuthenticatedException(RuntimeException ex) {
//        log.error("Erro de autenticação: {}", ex.getMessage());
//        var response = ExceptionResponse.builder()
//                .status(HttpStatus.UNAUTHORIZED.value())
//                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler({
//            ResourceNotFoundException.class,
//            FieldNotFoundException.class,
//            UserNotRegisteredException.class
//    })
//    public final ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
//        log.error("Recurso não encontrado: {}", ex.getMessage());
//        var response = ExceptionResponse.builder()
//                .status(HttpStatus.NOT_FOUND.value())
//                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler({
//            CpfAlreadyExistsException.class,
//            EmailAlreadyExistsException.class,
//            FieldAlreadyExistsException.class
//    })
//    public final ResponseEntity<ExceptionResponse> handleAlreadyExistsException(RuntimeException ex) {
//        log.error("Conflito de dados: {}", ex.getMessage());
//        var response = ExceptionResponse.builder()
//                .status(HttpStatus.CONFLICT.value())
//                .error(HttpStatus.CONFLICT.getReasonPhrase())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler({
//            ServiceException.class,
//            JWTServiceException.class
//    })
//    public final ResponseEntity<ExceptionResponse> handleServiceException(RuntimeException ex) {
//        log.error("Erro de serviço: {}", ex.getMessage());
//        var response = ExceptionResponse.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    // 400
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .toList();
//        log.error("Erro de validação: {}", errors);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsMap(errors));
//    }
//
//    private Map<String, List<String>> errorsMap(List<String> errors) {
//        Map<String, List<String>> errorResponse = new HashMap<>();
//        errorResponse.put("errors", errors);
//        return errorResponse;
//    }
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
//        List<String> errors = List.of(ex.getMessage());
//        log.error("Erro interno do servidor: {}", ex.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(errorsMap(errors));
//    }

}
