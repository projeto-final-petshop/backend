package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFoundException(Throwable cause) {
        super(cause);
    }
}
