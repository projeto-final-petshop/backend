package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException() {
        super("CPF já cadastrado.");
    }

}
