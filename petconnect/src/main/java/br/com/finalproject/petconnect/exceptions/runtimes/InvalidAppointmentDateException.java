package br.com.finalproject.petconnect.exceptions.runtimes;

public class InvalidAppointmentDateException extends RuntimeException {
    public InvalidAppointmentDateException(String message) {
        super(message);
    }
}
