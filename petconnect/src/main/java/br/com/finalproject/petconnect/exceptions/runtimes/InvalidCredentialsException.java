package br.com.finalproject.petconnect.exceptions.runtimes;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas.");
    }
}
