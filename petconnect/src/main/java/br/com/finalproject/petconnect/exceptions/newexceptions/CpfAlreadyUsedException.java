package br.com.finalproject.petconnect.exceptions.newexceptions;

public class CpfAlreadyUsedException extends RuntimeException {

    public CpfAlreadyUsedException(String message) {
        super(message);
    }
}
