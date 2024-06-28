package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class AppointmentTimeConflictException extends RuntimeException {

    public AppointmentTimeConflictException(String message) {
        super(message);
    }
}
