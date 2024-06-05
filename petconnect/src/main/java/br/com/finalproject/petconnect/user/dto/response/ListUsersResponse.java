package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersResponse {

    @JsonProperty("userId")
    private Long id;

    @Schema(description = "O nome do usuário",
            example = "João da Silva",
            minLength = 3, maxLength = 250,
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string")
    @Size(min = 3, max = 250, message = "name.size.message")
    private String name;

    @Schema(description = "Endereço de email do usuário.",
            example = "usario@dominio.com",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string", format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "invalid.email.format", regexp = "email.pattern.regexp")
    private String email;

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
            example = "+5511987654321", pattern =  "^\\+?\\d{13,14}$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string", format = "phone")
    @Pattern(regexp = "^\\+?\\d{13,14}$",
            message = "phoneNumber.message")
    private String phoneNumber;

    @Schema(description = "Indica se o usuário está ativo",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "boolean")
    private Boolean active;

    private RoleResponse role;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
