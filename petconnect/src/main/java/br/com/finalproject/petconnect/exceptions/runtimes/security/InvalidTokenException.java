package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String operation) {
        super("Token JWT " + operation);
    }

}
