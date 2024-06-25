package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

}
