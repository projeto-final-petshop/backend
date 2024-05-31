package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersResponse {

    @Schema(name = "userId", type = "Integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Número de identificação única do usuário")
    @JsonProperty("userId")
    private Long id;

    @Schema(name = "email", type = "String",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário",
            example = "username@domain.com")
    private String email;

    @Schema(name = "name", type = "String", minLength = 3,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Usuário")
    private String name;

    @Schema(name = "cpf", type = "String", minLength = 11, maxLength = 11,
            pattern = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do Usuário. Deve ser um número válido",
            example = "165.625.159-04")
    private String cpf;

    @Schema(name = "phoneNumber", type = "String", minLength = 13, maxLength = 14,
            description = "Número de telefone do Usuário",
            example = "+5544989529075")
    private String phoneNumber;

    @Schema(name = "active", type = "boolean",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Informa se o usuário está ativo ou não no sistema",
            example = "true")
    private boolean active;

    @Schema(name = "role", type = "string", enumAsRef = true,
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Tipo de permissão que o Usuário possui.")
    private RoleResponse role;

    @Schema(name = "createdAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Criação")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Schema(name = "updatedAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Atualização")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updatedAt;

}
