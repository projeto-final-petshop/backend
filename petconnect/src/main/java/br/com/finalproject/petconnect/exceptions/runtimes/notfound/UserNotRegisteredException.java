package br.com.finalproject.petconnect.exceptions.runtimes.notfound;

public class UserNotRegisteredException extends RuntimeException {

    public UserNotRegisteredException() {
        super("Usuário não cadastrado.");
    }
}
