package br.com.finalproject.petconnect.exceptions.runtimes;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String message) {
        super(message);
    }
}
