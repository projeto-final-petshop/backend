package br.com.finalproject.petconnect.exceptions.runtimes;

/**
 * HTTP STATUS 401 - unauthenticated
 */
public class UanuthenticatedUserException extends RuntimeException {

    public UanuthenticatedUserException() {
    }

    public UanuthenticatedUserException(String message) {
        super(message);
    }

    public UanuthenticatedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UanuthenticatedUserException(Throwable cause) {
        super(cause);
    }
}
