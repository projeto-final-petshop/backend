package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAccessException(Throwable cause) {
        super(cause);
    }
}
