package br.com.finalproject.petconnect.exceptions.runtimes.cpf;

public class CpfNotFoundException extends RuntimeException {

    public CpfNotFoundException(String message) {
        super(message);
    }
}
