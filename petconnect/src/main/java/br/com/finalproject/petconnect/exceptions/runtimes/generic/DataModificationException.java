package br.com.finalproject.petconnect.exceptions.runtimes.pet;

public class InvalidPetDataException extends RuntimeException {

    public InvalidPetDataException(String message) {
        super(message);
    }
}
