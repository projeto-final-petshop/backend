package br.com.finalproject.petconnect.exceptions.runtimes;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
