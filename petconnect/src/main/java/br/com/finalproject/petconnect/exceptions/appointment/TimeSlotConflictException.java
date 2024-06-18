package br.com.finalproject.petconnect.exceptions;

public class TimeSlotConflictException extends RuntimeException {

    public TimeSlotConflictException(String message) {
        super(message);
    }

    public TimeSlotConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeSlotConflictException(Throwable cause) {
        super(cause);
    }

    public TimeSlotConflictException() {
    }
}
