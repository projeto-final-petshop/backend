package br.com.finalproject.petconnect.exceptions.runtimes;

public class PasswordUpdateException extends RuntimeException {

    public PasswordUpdateException(String message) {
        super(message);
    }

    public PasswordUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
