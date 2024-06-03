package br.com.finalproject.petconnect.exceptions.runtimes.store;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String message) {
        super(message);
    }
}
