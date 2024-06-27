package br.com.finalproject.petconnect.exceptions.runtimes.security;

public class PetPermissionDeniedException extends RuntimeException {

    public PetPermissionDeniedException() {
        super("Usuário não tem permissão.");
    }

    public PetPermissionDeniedException(String operation) {
        super("Usuário não possui permissão para " + operation + " um animal de estimação.");
    }

}
