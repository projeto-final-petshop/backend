package br.com.projetofinal.petconnet.exceptions.errors.users;

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
