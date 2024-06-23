package br.com.finalproject.petconnect.exceptions.runtimes.conflict;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Usuário já cadastrado.");
    }

}
