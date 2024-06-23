package br.com.finalproject.petconnect.exceptions.runtimes;

public class JWTServiceException extends RuntimeException {

    public JWTServiceException() {
    }

    public JWTServiceException(String message) {
        super(message);
    }

    public JWTServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTServiceException(Throwable cause) {
        super(cause);
    }
}
