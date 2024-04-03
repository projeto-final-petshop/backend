package br.com.projetofinal.petconnet.core.exceptions.errors.pets;

public class PetListException extends RuntimeException {

    public PetListException() {
    }

    public PetListException(String message) {
        super(message);
    }

    public PetListException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetListException(Throwable cause) {
        super(cause);
    }
}
