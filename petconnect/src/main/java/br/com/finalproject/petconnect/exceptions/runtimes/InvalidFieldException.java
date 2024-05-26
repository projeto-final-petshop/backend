package br.com.finalproject.petconnect.exceptions.runtimes;

/**
 * HTTP STATUS 400 - Bad Request
 */
public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException() {
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFieldException(Throwable cause) {
        super(cause);
    }
}
