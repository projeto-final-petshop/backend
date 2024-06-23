package br.com.finalproject.petconnect.exceptions.runtimes;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
