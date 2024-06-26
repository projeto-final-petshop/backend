package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class UserInactiveException extends RuntimeException {

    public UserInactiveException() {
        super("Usuário inativo.");
    }

}
