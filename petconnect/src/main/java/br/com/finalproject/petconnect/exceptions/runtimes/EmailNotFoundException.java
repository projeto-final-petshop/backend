package br.com.finalproject.petconnect.exceptions.runtimes;

/**
 * HTTP STATUS 404 - Not Found
 */
public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(Throwable cause) {
        super(cause);
    }
}
