package br.com.finalproject.petconnect.exceptions.runtimes.notfound;

public class FieldNotFoundException extends RuntimeException {

    public FieldNotFoundException(String field) {
        super(field + " não encontrado.");
    }

}
