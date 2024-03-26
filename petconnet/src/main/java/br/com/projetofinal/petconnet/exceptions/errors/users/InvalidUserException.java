package br.com.projetofinal.petconnet.exceptions.errors.users;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
    }

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserException(Throwable cause) {
        super(cause);
    }
}
