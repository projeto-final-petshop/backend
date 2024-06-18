package br.com.finalproject.petconnect.exceptions;

public class NullQueryDateException extends RuntimeException {

    public NullQueryDateException(String message) {
        super(message);
    }

    public NullQueryDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullQueryDateException(Throwable cause) {
        super(cause);
    }

    public NullQueryDateException() {
    }
}
