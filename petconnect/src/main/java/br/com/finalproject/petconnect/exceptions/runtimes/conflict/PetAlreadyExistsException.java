package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class PetAlreadyExistsException extends RuntimeException {

    public PetAlreadyExistsException(String message) {
        super(message);
    }
}
