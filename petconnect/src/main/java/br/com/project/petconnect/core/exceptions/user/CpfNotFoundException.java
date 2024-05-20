package br.com.project.petconnect.core.exceptions.user;

public class CpfNotFoundException extends RuntimeException {

    public CpfNotFoundException() {
    }

    public CpfNotFoundException(String message) {
        super(message);
    }

    public CpfNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfNotFoundException(Throwable cause) {
        super(cause);
    }
}
