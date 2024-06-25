package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class EmailSendException extends RuntimeException {

    public EmailSendException() {
        super("Não foi possível enviar o e-mail. Tente novamente mais tarde.");
    }

}
