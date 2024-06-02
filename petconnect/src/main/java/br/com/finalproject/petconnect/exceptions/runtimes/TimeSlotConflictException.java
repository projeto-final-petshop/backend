package br.com.finalproject.petconnect.exceptions.runtimes;

public class TimeSlotConflictException extends RuntimeException {
    public TimeSlotConflictException(String message) {
        super(message);
    }
}
