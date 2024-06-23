package br.com.finalproject.petconnect.exceptions.runtimes;

public class FieldNotFoundException extends RuntimeException {

    public FieldNotFoundException(String field) {
        super(field + " não encontrado.");
    }

}
