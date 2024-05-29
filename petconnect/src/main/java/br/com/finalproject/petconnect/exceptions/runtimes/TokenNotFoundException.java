package br.com.finalproject.petconnect.exceptions.runtimes;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
