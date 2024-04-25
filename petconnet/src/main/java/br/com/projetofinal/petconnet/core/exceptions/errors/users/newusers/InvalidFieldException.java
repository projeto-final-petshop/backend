package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException() {
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFieldException(Throwable cause) {
        super(cause);
    }
}
