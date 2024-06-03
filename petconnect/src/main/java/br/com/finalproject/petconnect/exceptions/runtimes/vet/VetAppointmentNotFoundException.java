package br.com.finalproject.petconnect.exceptions.runtimes.vet;

public class VetAppointmentNotFoundException extends RuntimeException {

    public VetAppointmentNotFoundException(String message) {
        super(message);
    }
}
