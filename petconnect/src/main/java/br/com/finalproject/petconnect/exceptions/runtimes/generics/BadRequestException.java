package br.com.finalproject.petconnect.exceptions.runtimes.generics;

// Exception para erros de requisição inválida (Bad Request)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
