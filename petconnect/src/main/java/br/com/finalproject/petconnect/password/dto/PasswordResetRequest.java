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

    @Schema(name = "email", type = "String",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário", example = "username@domain.com")
    @Email
    private String email;

}
