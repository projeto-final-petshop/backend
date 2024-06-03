package br.com.finalproject.petconnect.exceptions.runtimes.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
