package br.com.projetofinal.petconnet.exceptions.errors.pets;

public class UnableToDeletePetException extends RuntimeException {
    public UnableToDeletePetException() {
    }

    public UnableToDeletePetException(String message) {
        super(message);
    }

    public UnableToDeletePetException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDeletePetException(Throwable cause) {
        super(cause);
    }
}
