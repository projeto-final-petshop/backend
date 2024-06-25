package br.com.finalproject.petconnect.exceptions.runtimes;

public class PastDateException extends RuntimeException {

    public PastDateException() {
        super("A data de agendamento não pode estar no passado.");
    }
}
