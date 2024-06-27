package br.com.finalproject.petconnect.exceptions.runtimes.generics;

// Exception para erros de acesso proibido (Forbidden)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
