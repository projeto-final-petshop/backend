package br.com.finalproject.petconnect.exceptions.runtimes;

public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
