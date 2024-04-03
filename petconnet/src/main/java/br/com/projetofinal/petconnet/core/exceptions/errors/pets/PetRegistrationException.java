package br.com.projetofinal.petconnet.core.exceptions.errors.pets;

public class PetRegistrationException extends RuntimeException {

    public PetRegistrationException() {
    }

    public PetRegistrationException(String message) {
        super(message);
    }

    public PetRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetRegistrationException(Throwable cause) {
        super(cause);
    }
}
