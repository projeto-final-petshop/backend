package br.com.finalproject.petconnect.exceptions.runtimes.notfound;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
