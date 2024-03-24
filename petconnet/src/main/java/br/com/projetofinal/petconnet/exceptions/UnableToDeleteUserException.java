package br.com.projetofinal.petconnet.exceptions;

public class UnableToDeleteUserException extends RuntimeException {

    public UnableToDeleteUserException() {
    }

    public UnableToDeleteUserException(String message) {
        super(message);
    }

    public UnableToDeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDeleteUserException(Throwable cause) {
        super(cause);
    }
}
