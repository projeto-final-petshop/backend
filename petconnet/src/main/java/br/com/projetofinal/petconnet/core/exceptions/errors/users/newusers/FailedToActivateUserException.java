package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class FailedToActivateUserException extends RuntimeException {

    public FailedToActivateUserException() {
    }

    public FailedToActivateUserException(String message) {
        super(message);
    }

    public FailedToActivateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToActivateUserException(Throwable cause) {
        super(cause);
    }
}
