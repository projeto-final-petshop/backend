package br.com.finalproject.petconnect.exceptions.runtimes;

public class UserInactiveException extends RuntimeException {

    public UserInactiveException() {
        super("Usuário inativo.");
    }
}
