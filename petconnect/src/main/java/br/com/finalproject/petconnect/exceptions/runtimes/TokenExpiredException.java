package br.com.finalproject.petconnect.exceptions.runtimes;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

}
