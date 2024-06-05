package br.com.finalproject.petconnect.exceptions.runtimes.generic;

public class PetConnectServiceException extends RuntimeException {

    public PetConnectServiceException() {
    }

    public PetConnectServiceException(String message) {
        super(message);
    }

    public PetConnectServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetConnectServiceException(Throwable cause) {
        super(cause);
    }
}
