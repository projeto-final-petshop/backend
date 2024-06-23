package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String fieldName, String message) {
        super("Valor inválido para campo '" + fieldName + "': " + message);
    }

    public InvalidRequestException(String fieldName, String message, Throwable cause) {
        super("Valor inválido para campo '" + fieldName + "': " + message, cause);
    }

}
