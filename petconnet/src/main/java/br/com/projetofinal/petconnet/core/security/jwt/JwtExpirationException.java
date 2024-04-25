package br.com.projetofinal.petconnet.core.security.jwt;

public class JwtExpirationException extends RuntimeException {
    public JwtExpirationException(String message) {
        super(message);
    }
}
