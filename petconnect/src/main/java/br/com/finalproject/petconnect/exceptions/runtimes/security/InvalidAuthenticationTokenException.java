package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String message) {
        super(message);
    }

    //    public InvalidAuthenticationTokenException() {
//        super("Token de autenticação inválido.");
//    }
//
//    public InvalidAuthenticationTokenException(String operation) {
//        super("Token " + operation);
//    }

}
