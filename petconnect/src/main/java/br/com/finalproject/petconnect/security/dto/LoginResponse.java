package br.com.finalproject.petconnect.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    @Schema(
            name = "token",
            type = "String",
            example = "eyJhbGciOiJIUzI1NiJ9...",
            description = "Token JWT para autenticação"
    )
    private String token;

    @Schema(
            name = "expiresIn",
            type = "integer",
            format = "int32",
            example = "86400000",
            description = "Tempo em milissegundos até o token expirar (24h)"
    )
    private long expiresIn;

}
