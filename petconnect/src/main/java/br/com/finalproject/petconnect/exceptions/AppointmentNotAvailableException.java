package br.com.finalproject.petconnect.exceptions;

public class AppointmentNotAvailableException extends RuntimeException {

    public AppointmentNotAvailableException(String message) {
        super(message);
    }
}
