package br.com.finalproject.petconnect.exceptions.runtimes.email;

public class EmailSendException extends RuntimeException {

    public EmailSendException(String message) {
        super(message);
    }
}
