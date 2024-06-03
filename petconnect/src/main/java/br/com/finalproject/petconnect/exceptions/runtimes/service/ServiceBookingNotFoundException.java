package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class ServiceBookingNotFoundException extends RuntimeException {

    public ServiceBookingNotFoundException(String message) {
        super(message);
    }
}
