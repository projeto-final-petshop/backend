package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class InvalidServiceBookingException extends RuntimeException {

    public InvalidServiceBookingException(String message) {
        super(message);
    }
}
