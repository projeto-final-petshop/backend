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

    @Schema(description = "O nome do usuário",
            example = "João da Silva",
            minLength = 3, maxLength = 250,
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string")
    private String name;

    @Schema(description = "Endereço de email do usuário.",
            example = "usario@dominio.com",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string", format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "Formato de email inválido")
    private String email;

    @Schema(description = "A senha deve conter pelo menos 8 caracteres, incluindo pelo menos uma letra maiúscula, " +
            "uma letra minúscula, um número e um caractere especial.",
            example = "SenhaForte@123",
            pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY,
            type = "string", format = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "A senha deve conter pelo menos 8 caracteres, incluindo letras, números e caracteres especiais")
    private String password;

    @Schema(description = "Confirmação da nova senha.",
            example = "SenhaForte@123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY,
            type = "string", format = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @Schema(description = "O CPF do usuário",
            example = "123.456.789-10",
            pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string", format = "cpf")
    @CPF(message = "invalid.cpf")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "cpf.message")
    private String cpf;

    @Schema(description = "O número de telefone do usuário",
            example = "+5511987654321", pattern = "^\\+?\\d{13,14}$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string", format = "phone")
    @Pattern(regexp = "^\\+?\\d{13,14}$",
            message = "Formato de número de telefone inválido")
    private String phoneNumber;

    @Schema(description = "Indica se o usuário está ativo",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "boolean")
    private Boolean active;

    @Schema(description = "O endereço do usuário",
            example = "Rua Exemplo, 123, Bairro Exemplo, Cidade Exemplo",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string")
    private String address;

    private Long role;

}
