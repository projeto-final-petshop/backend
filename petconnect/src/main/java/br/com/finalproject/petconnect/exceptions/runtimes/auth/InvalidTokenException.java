package br.com.finalproject.petconnect.exceptions.runtimes.auth;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
