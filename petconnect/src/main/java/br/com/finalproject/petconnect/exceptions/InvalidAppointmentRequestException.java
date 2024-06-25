package br.com.finalproject.petconnect.exceptions;

public class InvalidAppointmentRequestException extends RuntimeException {

    public InvalidAppointmentRequestException(String message) {
        super(message);
    }
}
