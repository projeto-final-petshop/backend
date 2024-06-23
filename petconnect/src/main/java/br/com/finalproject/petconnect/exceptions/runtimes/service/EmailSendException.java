package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class EmailSendException extends RuntimeException {

    public EmailSendException() {
        super("Erro ao enviar email para redefinição de senha.");
    }

}
