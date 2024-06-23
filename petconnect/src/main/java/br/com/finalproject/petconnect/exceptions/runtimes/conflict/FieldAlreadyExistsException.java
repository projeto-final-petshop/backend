package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class FieldAlreadyExistsException extends RuntimeException {

    public FieldAlreadyExistsException(String field) {
        super(field + " já cadastrado.");
    }
    
}
