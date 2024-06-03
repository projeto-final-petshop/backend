package br.com.finalproject.petconnect.exceptions.runtimes.pet;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(String message) {
        super(message);
    }
}
