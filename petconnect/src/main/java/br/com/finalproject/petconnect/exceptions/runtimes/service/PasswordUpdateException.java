package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class PasswordUpdateException extends RuntimeException {

    public PasswordUpdateException(String operation) {
        super("Erro ao " + operation + " senha.");
    }

}
