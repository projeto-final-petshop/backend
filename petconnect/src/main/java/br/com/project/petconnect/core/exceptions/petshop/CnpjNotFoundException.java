package br.com.project.petconnect.core.exceptions.petshop;

public class CnpjNotFoundException extends RuntimeException {

    public CnpjNotFoundException() {
    }

    public CnpjNotFoundException(String message) {
        super(message);
    }

    public CnpjNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnpjNotFoundException(Throwable cause) {
        super(cause);
    }
}
