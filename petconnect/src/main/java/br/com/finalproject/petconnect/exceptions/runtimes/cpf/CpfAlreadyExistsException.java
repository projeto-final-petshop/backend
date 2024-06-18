package br.com.finalproject.petconnect.exceptions.runtimes.cpf;

public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException(String message) {
        super(message);
    }

    public CpfAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CpfAlreadyExistsException() {
    }
}
