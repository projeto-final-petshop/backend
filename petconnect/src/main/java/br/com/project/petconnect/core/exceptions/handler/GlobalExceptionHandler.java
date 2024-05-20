package br.com.project.petconnect.core.exceptions.handler;

import br.com.project.petconnect.core.exceptions.dto.ErrorResponse;
import br.com.project.petconnect.core.exceptions.dto.ErrorStatus;
import br.com.project.petconnect.core.exceptions.generics.EmailAlreadyRegisteredException;
import br.com.project.petconnect.core.exceptions.generics.EmailNotFoundException;
import br.com.project.petconnect.core.exceptions.generics.InvalidFieldException;
import br.com.project.petconnect.core.exceptions.generics.InvalidIdException;
import br.com.project.petconnect.core.exceptions.owner.OwnerNotFoundException;
import br.com.project.petconnect.core.exceptions.pet.PetNotFoundException;
import br.com.project.petconnect.core.exceptions.petshop.CnpjAlreadyRegisteredException;
import br.com.project.petconnect.core.exceptions.petshop.CnpjNotFoundException;
import br.com.project.petconnect.core.exceptions.petshop.PetShopNotFoundException;
import br.com.project.petconnect.core.exceptions.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Esta classe é um tratador global de exceções para uma aplicação Spring Boot. Ela intercepta exceções específicas e
 * retorna respostas de erro padronizadas com o código HTTP apropriado.
 * <p>
 * &#64;Slf4j: habilita o uso do registro SLF4J para ‘log’.
 * <p>
 * &#64;RestControllerAdvice: indica que esta classe é um controlador de conselhos para controladores REST, permitindo a
 * interceptação de exceções.
 *
 * @author juliane.maran
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // ################################## STATUS 400 - BAD REQUEST ##########################################

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFieldException(InvalidFieldException ex) {
        log.info("Campo inválido {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.INVALID_FIELD);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserIdException(InvalidIdException ex) {
        log.info("ID inválido {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.INVALID_FIELD);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        log.info("Senha inválida {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.INVALID_PASSWORD);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex) {
        log.info("Senhas não conferem {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.PASSWORDS_DO_NOT_MATCH);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    // ##################################### STATUS 404 - NOT FOUND ###################################################

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.info("Usuário não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.USER_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOwnerNotFoundException(OwnerNotFoundException ex) {
        log.info("Tutor não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.OWNER_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePetNotFoundException(PetNotFoundException ex) {
        log.info("Pet não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.PET_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(PetShopNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePetShopNotFoundException(PetShopNotFoundException ex) {
        log.info("PetShop não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.PETSHOP_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        log.info("Email não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.EMAIL_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(CpfNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCpfNotFoundException(CpfNotFoundException ex) {
        log.info("CPF não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.CPF_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(CnpjNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCnpjNotFoundException(CnpjNotFoundException ex) {
        log.info("CNPJ não encontrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.CNPJ_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    // ################################## STATUS 409 - CONFLICT ##########################################

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        log.info("Email já cadastrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.EMAIL_ALREADY_REGISTERED);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(CpfAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleCpfAlreadyRegisteredException(CpfAlreadyRegisteredException ex) {
        log.info("CPF já cadastrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.CPF_ALREADY_REGISTERED);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(CnpjAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleCnpjAlreadyRegisteredException(CnpjAlreadyRegisteredException ex) {
        log.info("CNPJ já cadastrado {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.CNPJ_ALREADY_REGISTERED);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    // ################################## STATUS 500 - INTERNAL SERVER ERROR ##########################################

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerErrorException(Exception ex) {
        log.info("Erro interno no servidor {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ErrorStatus.SERVER_ERROR);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}
