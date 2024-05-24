package br.com.project.petconnect.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "LoginResponse", description = "Parâmetros necessários para retornar as informações do Usuário Autenticado")
public class LoginResponse {

    private String token;
    private long expiresIn;

}
