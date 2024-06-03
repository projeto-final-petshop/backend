package br.com.finalproject.petconnect.exceptions.runtimes.user;

public class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException(String message) {
        super(message);
    }
}
