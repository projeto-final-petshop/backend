package br.com.finalproject.petconnect.exceptions.runtimes.auth;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message) {
        super(message);
    }
}
