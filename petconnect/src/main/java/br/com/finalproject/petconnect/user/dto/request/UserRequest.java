package br.com.finalproject.petconnect.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Schema(
            name = "email",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário"
    )
    @Email
    private String email;

    @Schema(
            name = "password",
            type = "String",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Senha do Usuário"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String password;

    @Schema(
            name = "confirmPassword",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Confirmar senha"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @Schema(
            name = "name",
            type = "String",
            minLength = 3,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Usuário"
    )
    private String name;

    @Schema(
            name = "cpf",
            type = "String",
            minLength = 11,
            maxLength = 11,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do Usuário. Deve ser um número válido"
    )
    @CPF
    private String cpf;

    @Schema(
            name = "phoneNumber",
            type = "String",
            minLength = 13,
            maxLength = 14,
            description = "Número de telefone do Usuário"
    )
    private String phoneNumber;

    @Schema(
            name = "active",
            type = "boolean",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Informa se o usuário está ativo ou não no sistema"
    )
    private boolean active;

    @Schema(
            name = "roleId",
            type = "Integer",
            format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Número de identificação única da Role"
    )
    private Long roleId;

}
