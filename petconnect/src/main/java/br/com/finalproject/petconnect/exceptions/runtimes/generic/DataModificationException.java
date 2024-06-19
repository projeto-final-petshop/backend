package br.com.finalproject.petconnect.exceptions.runtimes.generic;

public class DataModificationException extends RuntimeException {

    public DataModificationException(String message) {
        super(message);
    }

    public DataModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataModificationException(Throwable cause) {
        super(cause);
    }

    public DataModificationException() {
    }
}
