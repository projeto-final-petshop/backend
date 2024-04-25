package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class FailedToInactivateUserException extends RuntimeException {

    public FailedToInactivateUserException() {
    }

    public FailedToInactivateUserException(String message) {
        super(message);
    }

    public FailedToInactivateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToInactivateUserException(Throwable cause) {
        super(cause);
    }
}
