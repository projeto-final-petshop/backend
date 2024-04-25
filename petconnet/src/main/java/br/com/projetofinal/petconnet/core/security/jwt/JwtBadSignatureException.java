package br.com.projetofinal.petconnet.core.security.jwt;

public class JwtBadSignatureException extends RuntimeException {
    public JwtBadSignatureException(String message) {
        super(message);
    }
}
