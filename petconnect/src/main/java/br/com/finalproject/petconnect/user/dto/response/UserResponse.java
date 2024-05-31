package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(name = "userId", type = "Integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Número de identificação única do usuário")
    @JsonProperty("userId")
    private Long id;

    @Schema(name = "name", type = "String", example = "Maria Caroline Gabrielly Peixoto")
    private String name;

    @Schema(name = "email", type = "String", example = "username@domain.com")
    private String email;

    @Schema(name = "cpf", type = "String", example = "165.625.159-04")
    private String cpf;

    @Schema(name = "phoneNumber", type = "String", example = "+5544989529075")
    private String phoneNumber;

    @Schema(name = "active", type = "boolean", example = "true")
    private boolean active;

    @Schema(name = "role", type = "string", enumAsRef = true,
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Tipo de permissão que o Usuário possui.")
    private RoleResponse role;

    @Schema(name = "createdAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Criação", example = "31/05/2024 19:12:17")
    private OffsetDateTime createdAt;

    @Schema(name = "updatedAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Atualização", example = "31/05/2024 19:12:17")
    private OffsetDateTime updatedAt;

}
