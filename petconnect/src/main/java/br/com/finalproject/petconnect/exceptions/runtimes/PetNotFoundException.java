package br.com.finalproject.petconnect.exceptions.runtimes;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(String message) {
        super(message);
    }

}
