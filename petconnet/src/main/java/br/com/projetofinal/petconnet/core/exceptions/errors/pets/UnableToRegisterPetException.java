package br.com.projetofinal.petconnet.core.exceptions.errors.pets;

public class UnableToRegisterPetException extends RuntimeException {
    public UnableToRegisterPetException() {
    }

    public UnableToRegisterPetException(String message) {
        super(message);
    }

    public UnableToRegisterPetException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToRegisterPetException(Throwable cause) {
        super(cause);
    }
}
