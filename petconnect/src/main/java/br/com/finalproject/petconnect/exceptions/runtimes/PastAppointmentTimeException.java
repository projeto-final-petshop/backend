package br.com.finalproject.petconnect.exceptions.runtimes;

public class PastTimeException extends RuntimeException {

    public PastTimeException() {
        super("O horário do agendamento não pode estar no passado.");
    }
}
