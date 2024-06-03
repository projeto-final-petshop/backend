package br.com.finalproject.petconnect.exceptions.runtimes.email;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String message) {
        super(message);
    }
}
