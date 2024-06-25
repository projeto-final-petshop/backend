package br.com.finalproject.petconnect.exceptions.runtimes;

public class PastAppointmentDateException extends RuntimeException {

    public PastAppointmentDateException() {
        super("A data de agendamento não pode estar no passado.");
    }
}
