package br.com.finalproject.petconnect.exceptions.runtimes.generic;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String message) {
        super(message);
    }
}
