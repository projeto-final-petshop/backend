package br.com.finalproject.petconnect.exceptions.dto;

import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    /**
     * Bad Request - Http Status 400
     */
    INVALID_FIELD("INVALID_FIELD", HttpStatus.BAD_REQUEST), // InvalidFieldException.class
    NO_SEARCH_CRITERIA_PROVIDED("NO_SEARCH_CRITERIA_PROVIDED", HttpStatus.BAD_REQUEST), // NoSearchCriteriaProvidedException.class
    PASSWORD_UPDATE("PASSWORD_UPDATE", HttpStatus.BAD_REQUEST), // PasswordUpdateException.class

    /**
     * UNAUTHORIZED - Http Status 401
     */
    AUTHENTICATION("AUTHENTICATION", HttpStatus.UNAUTHORIZED), // AuthenticationException.class
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", HttpStatus.UNAUTHORIZED), // TokenNotFoundException.class
    UNAUTHENTICATED("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED), // UnauthenticateException.class
    BAD_CREDENTIALS("BAD_CREDENTIALS", HttpStatus.UNAUTHORIZED), // BadCredentialsException.class
    INVALID_SIGNATURE("INVALID_SIGNATURE", HttpStatus.UNAUTHORIZED), // InvalidSignatureException.class
    TOKEN_EXPIRED("TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED), // TokenExpiredException.class

    /**
     * FORBIDDEN - Http Status 403
     */
    PERMISSION_DENIED("PERMISSION_DENIED", HttpStatus.FORBIDDEN), // PermissionDeniedException.class
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", HttpStatus.FORBIDDEN), // UserNotActiveException.class
    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN), // AccessDeniedException.class

    /**
     * Not Found - Http Status 404
     */
    CPF_NOT_FOUND("CPF_NOT_FOUND", HttpStatus.NOT_FOUND), // CpfNotFoundException.class
    EMAIL_NOT_FOUND("EMAIL_NOT_FOUND", HttpStatus.NOT_FOUND), // EmailNotFoundException.class
    NO_PETS_FOUND("NO_PETS_FOUND", HttpStatus.NOT_FOUND), // NoPetsFoundException.class
    PET_NOT_FOUND("PET_NOT_FOUND", HttpStatus.NOT_FOUND), // PetNotFoundException.class
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND), // RoleNotFoundException.class
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND), // UserNotFoundException.class
    NO_INACTIVE_USERS_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND), // NoInactiveUsersFoundException.class

    /**
     * Conflict - Http Status 409
     */
    CPF_ALREADY_EXISTS("CPF_ALREADY_EXISTS", HttpStatus.CONFLICT), // CpfAlreadyExistsException.class
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", HttpStatus.CONFLICT), // EmailAlreadyExistsException.class

    /**
     * LOCKED - Http Status 423
     */
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", HttpStatus.LOCKED), // AccountLockedException.class

    /**
     * INTERNAL_SERVER_ERROR - Http Status 500
     */
    PET_SERVICE("PET_SERVICE", HttpStatus.INTERNAL_SERVER_ERROR), // PetServiceException.class
    UNKNOWN_ERROR("UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR); // UnknownErrorException.class

    private final String messageKey;
    private final HttpStatus status;

    public String getMessage(MessageUtil messageUtil) {
        return messageUtil.getMessage(messageKey);
    }

}

