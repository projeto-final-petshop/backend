package br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers;

public class DocumentNumberAlreadyExistsException extends RuntimeException {

    public DocumentNumberAlreadyExistsException() {
    }

    public DocumentNumberAlreadyExistsException(String message) {
        super(message);
    }

    public DocumentNumberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentNumberAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
