package br.com.finalproject.petconnect.exceptions.runtimes;

public class PastAppointmentTimeException extends RuntimeException {

    public PastAppointmentTimeException() {
        super("O horário do agendamento não pode estar no passado.");
    }
}
