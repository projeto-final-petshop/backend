package br.com.finalproject.petconnect.exceptions.runtimes;

public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String message) {
        super(message);
    }

    public InvalidAuthenticationTokenException() {
        super("Token de autenticação inválido.");
    }

}
