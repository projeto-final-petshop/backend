package br.com.finalproject.petconnect.exceptions.runtimes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UanuthenticatedUserException extends RuntimeException {

    public UanuthenticatedUserException() {
    }

    public UanuthenticatedUserException(String message) {
        super(message);
    }

    public UanuthenticatedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UanuthenticatedUserException(Throwable cause) {
        super(cause);
    }
}
