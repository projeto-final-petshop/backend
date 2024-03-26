package br.com.projetofinal.petconnet.exceptions.errors.users;

public class UnableToSavedUserException extends RuntimeException {

    public UnableToSavedUserException() {
    }

    public UnableToSavedUserException(String message) {
        super(message);
    }

    public UnableToSavedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToSavedUserException(Throwable cause) {
        super(cause);
    }
}
