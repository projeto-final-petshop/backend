package br.com.finalproject.petconnect.password.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @Schema(
            description = "password.currentPassword",
            example = "SenhaAtual@123",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            type = "string",
            format = "password",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String currentPassword;

    @Schema(
            description = "password.pattern.message",
            example = "password.example",
            pattern = "password.pattern.regexp",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            type = "string",
            format = "password",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    @Schema(
            description = "password.confirmPassword",
            example = "password.example",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            type = "string",
            format = "password",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

}
