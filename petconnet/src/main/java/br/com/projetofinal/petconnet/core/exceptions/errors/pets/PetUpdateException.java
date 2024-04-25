package br.com.projetofinal.petconnet.core.exceptions.errors.pets;

public class PetUpdateException extends RuntimeException {

    public PetUpdateException() {
    }

    public PetUpdateException(String message) {
        super(message);
    }

    public PetUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetUpdateException(Throwable cause) {
        super(cause);
    }
}
