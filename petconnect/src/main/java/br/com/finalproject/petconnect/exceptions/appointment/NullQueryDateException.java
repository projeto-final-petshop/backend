package br.com.finalproject.petconnect.exceptions.appointment;

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
