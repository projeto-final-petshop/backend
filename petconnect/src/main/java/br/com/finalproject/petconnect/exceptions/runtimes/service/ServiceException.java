package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class ServiceException extends RuntimeException {

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
