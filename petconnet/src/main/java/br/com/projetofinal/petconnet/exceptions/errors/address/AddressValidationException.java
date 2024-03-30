package br.com.projetofinal.petconnet.exceptions.errors.address;

public class AddressValidationException extends RuntimeException {

    public AddressValidationException() {
    }

    public AddressValidationException(String message) {
        super(message);
    }

    public AddressValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressValidationException(Throwable cause) {
        super(cause);
    }
}
