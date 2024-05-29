package br.com.finalproject.petconnect.exceptions.runtimes;

public class NoPetsFoundException extends RuntimeException {

    public NoPetsFoundException(String message) {
        super(message);
    }

}
