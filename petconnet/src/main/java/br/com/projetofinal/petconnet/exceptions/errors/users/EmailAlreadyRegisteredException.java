package br.com.projetofinal.petconnet.exceptions.errors.users;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException() {
    }

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public EmailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
