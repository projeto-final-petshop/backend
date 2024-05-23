package br.com.project.petconnect.core.exceptions.petshop;

public class CnpjAlreadyRegisteredException extends RuntimeException {
    public CnpjAlreadyRegisteredException() {
    }

    public CnpjAlreadyRegisteredException(String message) {
        super(message);
    }

    public CnpjAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnpjAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
