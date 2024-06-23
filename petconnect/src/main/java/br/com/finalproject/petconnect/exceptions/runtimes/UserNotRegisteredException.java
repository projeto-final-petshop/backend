package br.com.finalproject.petconnect.exceptions.runtimes;

public class UserNotRegisteredException extends RuntimeException {

    public UserNotRegisteredException() {
        super("Usuário não cadastrado.");
    }
}
