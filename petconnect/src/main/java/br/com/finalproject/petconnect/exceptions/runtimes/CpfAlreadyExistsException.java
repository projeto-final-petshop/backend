package br.com.finalproject.petconnect.exceptions.runtimes;

/**
 * HTTP STATUS 409 - Conflict
 */
public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException() {
    }

    public CpfAlreadyExistsException(String message) {
        super(message);
    }

    public CpfAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
