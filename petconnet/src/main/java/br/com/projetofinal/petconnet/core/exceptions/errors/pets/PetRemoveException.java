package br.com.projetofinal.petconnet.core.exceptions.errors.pets;

public class PetRemoveException extends RuntimeException {

    public PetRemoveException() {
    }

    public PetRemoveException(String message) {
        super(message);
    }

    public PetRemoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetRemoveException(Throwable cause) {
        super(cause);
    }
}
