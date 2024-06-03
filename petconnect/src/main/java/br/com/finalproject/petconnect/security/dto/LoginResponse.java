package br.com.finalproject.petconnect.security.dto;

public record LoginResponse(String token,
                            long expiresIn) {

}
