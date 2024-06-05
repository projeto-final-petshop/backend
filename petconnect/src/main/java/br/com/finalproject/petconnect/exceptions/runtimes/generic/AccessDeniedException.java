package br.com.finalproject.petconnect.exceptions.runtimes.generic;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
