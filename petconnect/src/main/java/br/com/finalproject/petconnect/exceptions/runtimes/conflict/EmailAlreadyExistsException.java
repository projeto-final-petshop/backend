package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Já existe um usuário com este e-mail.");
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
