package br.com.project.petconnect.core.exceptions.generics;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(Throwable cause) {
        super(cause);
    }
}
