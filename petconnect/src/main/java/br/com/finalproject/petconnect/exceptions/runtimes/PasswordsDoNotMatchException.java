package br.com.finalproject.petconnect.exceptions.runtimes;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException() {
        super("As senhas não coincidem.");
    }
}
