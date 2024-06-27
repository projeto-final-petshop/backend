package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException() {
        super("As senhas não coincidem.");
    }
}
