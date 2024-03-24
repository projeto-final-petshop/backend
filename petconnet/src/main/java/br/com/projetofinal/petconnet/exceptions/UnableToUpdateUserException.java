package br.com.projetofinal.petconnet.exceptions;

public class UnableToUpdateUserException extends RuntimeException {

    public UnableToUpdateUserException() {
    }

    public UnableToUpdateUserException(String message) {
        super(message);
    }

    public UnableToUpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToUpdateUserException(Throwable cause) {
        super(cause);
    }
}
