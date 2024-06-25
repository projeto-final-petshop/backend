package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String operation, String fieldName) {
        super("Usuário não possui permissão para " + operation + " " + fieldName + ".");
    }

}
