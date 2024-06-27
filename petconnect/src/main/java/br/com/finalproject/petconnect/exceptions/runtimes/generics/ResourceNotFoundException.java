package br.com.finalproject.petconnect.exceptions.runtimes.generics;

// Exception para recursos não encontrados (Not Found)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
