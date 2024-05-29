package br.com.finalproject.petconnect.exceptions.runtimes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPetsFoundException extends RuntimeException {

    public NoPetsFoundException() {
    }

    public NoPetsFoundException(String message) {
        super(message);
    }

    public NoPetsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPetsFoundException(Throwable cause) {
        super(cause);
    }
}
