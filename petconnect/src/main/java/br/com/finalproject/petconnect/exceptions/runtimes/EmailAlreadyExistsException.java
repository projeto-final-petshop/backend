package br.com.finalproject.petconnect.exceptions.runtimes;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
