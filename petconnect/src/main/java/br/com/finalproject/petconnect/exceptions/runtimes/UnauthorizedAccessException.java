package br.com.finalproject.petconnect.exceptions.runtimes;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
