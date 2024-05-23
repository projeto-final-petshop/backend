package br.com.project.petconnect.core.exceptions.petshop;

public class PetShopNotFoundException extends RuntimeException {

    public PetShopNotFoundException() {
    }

    public PetShopNotFoundException(String message) {
        super(message);
    }

    public PetShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetShopNotFoundException(Throwable cause) {
        super(cause);
    }
}
