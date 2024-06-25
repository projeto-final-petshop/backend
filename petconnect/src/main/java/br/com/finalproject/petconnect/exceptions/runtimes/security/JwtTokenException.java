package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class JwtTokenException extends RuntimeException {

    public JwtTokenException() {
        super("O token de redefinição de senha expirou.");
    }

}
