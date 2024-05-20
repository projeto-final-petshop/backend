package br.com.project.petconnect.core.exceptions.user;

public class CpfAlreadyRegisteredException extends RuntimeException {
    public CpfAlreadyRegisteredException() {
    }

    public CpfAlreadyRegisteredException(String message) {
        super(message);
    }

    public CpfAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
