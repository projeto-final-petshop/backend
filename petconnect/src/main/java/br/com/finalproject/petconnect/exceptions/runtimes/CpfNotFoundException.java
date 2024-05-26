package br.com.finalproject.petconnect.exceptions.runtimes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
