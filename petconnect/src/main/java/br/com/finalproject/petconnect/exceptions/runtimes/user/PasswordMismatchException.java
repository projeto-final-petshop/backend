package br.com.finalproject.petconnect.exceptions.runtimes.user;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
