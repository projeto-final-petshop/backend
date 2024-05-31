package br.com.finalproject.petconnect.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(
            name = "email",
            type = "String",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário",
            example = "username@domain.com"
    )
    @Email
    private String email;

    @Schema(
            name = "password",
            type = "String",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Senha do Usuário",
            example = "P4$$w0rD"
    )
    private String password;

}
