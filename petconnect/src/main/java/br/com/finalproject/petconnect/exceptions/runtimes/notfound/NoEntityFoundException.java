package br.com.finalproject.petconnect.exceptions.runtimes.notfound;

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException(String fieldName) {
        super("Nenhum " + fieldName + " cadastrado.");
    }

}
