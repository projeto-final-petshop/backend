package br.com.projetofinal.petconnet.exceptions.errors.users;

public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException() {
    }

    public InvalidCredentialException(String message) {
        super(message);
    }

    public InvalidCredentialException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialException(Throwable cause) {
        super(cause);
    }
}
