package br.com.finalproject.petconnect.exceptions.runtimes.vet;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
