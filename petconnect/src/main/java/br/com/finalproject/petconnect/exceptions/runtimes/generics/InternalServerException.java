package br.com.finalproject.petconnect.exceptions.runtimes.generics;

// Exception para erros internos do servidor
public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
