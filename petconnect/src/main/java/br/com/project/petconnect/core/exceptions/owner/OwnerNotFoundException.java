package br.com.project.petconnect.core.exceptions.owner;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }

    public OwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnerNotFoundException(Throwable cause) {
        super(cause);
    }
}
