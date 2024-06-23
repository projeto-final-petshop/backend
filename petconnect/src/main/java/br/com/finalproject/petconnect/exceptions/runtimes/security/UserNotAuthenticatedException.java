package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException() {
        super("Usuário não autenticado.");
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
