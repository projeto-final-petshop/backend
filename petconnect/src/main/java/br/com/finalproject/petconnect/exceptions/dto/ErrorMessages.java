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
    INVALID_FIELD("badRequest.invalidField", HttpStatus.BAD_REQUEST), // InvalidFieldException.class
    NO_SEARCH_CRITERIA_PROVIDED("badRequest.noSearchCriteriaProvided", HttpStatus.BAD_REQUEST), // NoSearchCriteriaProvidedException.class
    PASSWORD_UPDATE("badRequest.passwordUpdate", HttpStatus.BAD_REQUEST), // PasswordUpdateException.class

    /**
     * UNAUTHORIZED - Http Status 401
     */
    AUTHENTICATION("unautorized.authentication", HttpStatus.UNAUTHORIZED), // AuthenticationException.class
    TOKEN_NOT_FOUND("unautorized.tokenNotFound", HttpStatus.UNAUTHORIZED), // TokenNotFoundException.class
    UNAUTHENTICATED("unautorized.unauthenticated", HttpStatus.UNAUTHORIZED), // UnauthenticateException.class
    BAD_CREDENTIALS("unautorized.badCredentials", HttpStatus.UNAUTHORIZED), // BadCredentialsException.class
    INVALID_SIGNATURE("unautorized.invalidSignature", HttpStatus.UNAUTHORIZED), // InvalidSignatureException.class
    TOKEN_EXPIRED("unautorized.tokenExpired", HttpStatus.UNAUTHORIZED), // TokenExpiredException.class

    /**
     * FORBIDDEN - Http Status 403
     */
    PERMISSION_DENIED("forbidden.permissionDenied", HttpStatus.FORBIDDEN), // PermissionDeniedException.class
    USER_NOT_ACTIVE("forbidden.userNotActive", HttpStatus.FORBIDDEN), // UserNotActiveException.class
    ACCESS_DENIED("forbidden.accessDenied", HttpStatus.FORBIDDEN), // AccessDeniedException.class

    /**
     * Not Found - Http Status 404
     */
    CPF_NOT_FOUND("notFound.cpf", HttpStatus.NOT_FOUND), // CpfNotFoundException.class
    EMAIL_NOT_FOUND("notFound.email", HttpStatus.NOT_FOUND), // EmailNotFoundException.class
    NO_PETS_FOUND("notFound.noPetsFound", HttpStatus.NOT_FOUND), // NoPetsFoundException.class
    PET_NOT_FOUND("PET_NOT_FOUND", HttpStatus.NOT_FOUND), // PetNotFoundException.class
    ROLE_NOT_FOUND("notFound.role", HttpStatus.NOT_FOUND), // RoleNotFoundException.class
    USER_NOT_FOUND("notFound.user", HttpStatus.NOT_FOUND), // UserNotFoundException.class
    NO_INACTIVE_USERS_FOUND("notFound.user", HttpStatus.NOT_FOUND), // NoInactiveUsersFoundException.class

    /**
     * Conflict - Http Status 409
     */
    CPF_ALREADY_EXISTS("conflict.cpf.alreadyExists", HttpStatus.CONFLICT), // CpfAlreadyExistsException.class
    EMAIL_ALREADY_EXISTS("conflict.email.alreadyExists", HttpStatus.CONFLICT), // EmailAlreadyExistsException.class

    /**
     * LOCKED - Http Status 423
     */
    ACCOUNT_LOCKED("locked.accountLocked", HttpStatus.LOCKED), // AccountLockedException.class

    /**
     * INTERNAL_SERVER_ERROR - Http Status 500
     */
    PET_SERVICE("internalServerError.petService", HttpStatus.INTERNAL_SERVER_ERROR), // PetServiceException.class
    UNKNOWN_ERROR("internalServerError.unknownError", HttpStatus.INTERNAL_SERVER_ERROR); // UnknownErrorException.class

    private final String messageKey;
    private final HttpStatus status;

    public String getMessage(MessageUtil messageUtil) {
        return messageUtil.getMessage(messageKey);
    }

}

