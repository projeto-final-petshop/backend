package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token JWT inválido.");
    }

}
