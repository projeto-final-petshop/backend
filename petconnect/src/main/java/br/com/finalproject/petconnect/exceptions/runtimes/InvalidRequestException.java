package br.com.finalproject.petconnect.exceptions.runtimes;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
