package br.com.finalproject.petconnect.exceptions.appointment;

public class WeekendNotAllowedException extends RuntimeException {

    public WeekendNotAllowedException(String message) {
        super(message);
    }

    public WeekendNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeekendNotAllowedException(Throwable cause) {
        super(cause);
    }

    public WeekendNotAllowedException() {
    }
}
