package br.com.project.petconnect.core.exceptions.generics;

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
