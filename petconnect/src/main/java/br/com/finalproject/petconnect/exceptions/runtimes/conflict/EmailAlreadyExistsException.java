package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("E-mail já cadatrado.");
    }

}
