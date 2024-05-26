package br.com.finalproject.petconnect.exceptions.runtimes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordUpdateException extends RuntimeException {

    public PasswordUpdateException(String message) {
        super(message);
    }

    public PasswordUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
