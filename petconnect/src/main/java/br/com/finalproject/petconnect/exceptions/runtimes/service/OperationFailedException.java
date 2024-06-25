package br.com.finalproject.petconnect.exceptions.runtimes.service;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException(String operation, String fieldName) {
        super("Erro ao " + operation + " " + fieldName + ".");
    }

}
