package br.com.finalproject.petconnect.exceptions.runtimes.generics;

// Exception para conflitos de dados (Conflict)
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
