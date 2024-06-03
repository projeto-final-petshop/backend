package br.com.finalproject.petconnect.exceptions.runtimes.password;

public class PasswordResetTokenInvalidException extends RuntimeException {

    public PasswordResetTokenInvalidException(String message) {
        super(message);
    }
}
