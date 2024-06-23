package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas.");
    }
}
