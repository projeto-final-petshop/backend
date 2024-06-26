package br.com.finalproject.petconnect.exceptions.runtimes.badrequest;

public class JWTFieldExtractionFailureException extends RuntimeException {

    public JWTFieldExtractionFailureException(String field) {
        super("Erro ao extrair " + field + " do token JWT.");
    }
    
}
