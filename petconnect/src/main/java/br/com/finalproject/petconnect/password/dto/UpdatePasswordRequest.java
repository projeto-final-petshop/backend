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

    @Schema(name = "currentPassword", type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Confirmar senha", example = "s3nh@A7uaL")
    private String currentPassword;

    @Schema(name = "newPassword", type = "String",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nova senha", example = "P4$$w0rD")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    @Schema(name = "confirmPassword", type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Confirmar senha", example = "P4$$w0rD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

}
