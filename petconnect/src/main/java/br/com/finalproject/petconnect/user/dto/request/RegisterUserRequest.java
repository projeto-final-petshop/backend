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
public class RegisterUserRequest {

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
            name = "newPassword",
            type = "String",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nova senha",
            example = "P4$$w0rD"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String password;

    @Schema(
            name = "confirmPassword",
            type = "String",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Confirmar Senha",
            example = "P4$$w0rD"
    )
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
            pattern = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do Usuário. Deve ser um número válido",
            example = "165.625.159-04"
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

}
