package br.com.finalproject.petconnect.password.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    @Schema(
            description = "Endereço de email do usuário.",
            example = "usario@dominio.com",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Email(message = "email.message",
            regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

}
