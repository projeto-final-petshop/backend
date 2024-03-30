package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException() {
    }

    public InactiveUserException(String message) {
        super(message);
    }

    public InactiveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InactiveUserException(Throwable cause) {
        super(cause);
    }
}
