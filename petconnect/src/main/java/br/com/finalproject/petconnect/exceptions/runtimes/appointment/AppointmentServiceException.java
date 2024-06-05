package br.com.finalproject.petconnect.exceptions.runtimes.appointment;

public class AppointmentServiceException extends RuntimeException {

    public AppointmentServiceException() {
    }

    public AppointmentServiceException(String message) {
        super(message);
    }

    public AppointmentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentServiceException(Throwable cause) {
        super(cause);
    }
}
