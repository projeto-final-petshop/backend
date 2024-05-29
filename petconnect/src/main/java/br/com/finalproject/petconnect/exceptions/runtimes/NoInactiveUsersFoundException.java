package br.com.finalproject.petconnect.exceptions.runtimes;

public class NoInactiveUsersFoundException extends RuntimeException {

    public NoInactiveUsersFoundException(String message) {
        super(message);
    }

}
