package br.com.finalproject.petconnect.exceptions.runtimes;

/**
 * HTTP STATUS 404 - Not Found
 */
public class CpfNotFoundException extends RuntimeException {

    public CpfNotFoundException() {
    }

    public CpfNotFoundException(String message) {
        super(message);
    }

    public CpfNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfNotFoundException(Throwable cause) {
        super(cause);
    }
}
