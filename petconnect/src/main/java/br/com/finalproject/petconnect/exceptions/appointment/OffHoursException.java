package br.com.finalproject.petconnect.exceptions.appointment;

public class OffHoursException extends RuntimeException {

    public OffHoursException(String message) {
        super(message);
    }

    public OffHoursException(String message, Throwable cause) {
        super(message, cause);
    }

    public OffHoursException(Throwable cause) {
        super(cause);
    }

    public OffHoursException() {
    }
}
