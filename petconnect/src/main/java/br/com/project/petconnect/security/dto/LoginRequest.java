package br.com.project.petconnect.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "LoginRequest", description = "Parâmetros necessários efetuar o Login", required = true)
public class LoginRequest {

    private String email;
    private String password;

}
