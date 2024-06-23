package br.com.finalproject.petconnect.exceptions.runtimes;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException() {
        super("Usuário não autenticado.");
    }
}
