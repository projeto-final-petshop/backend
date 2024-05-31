package br.com.finalproject.petconnect.roles.dto;

import br.com.finalproject.petconnect.roles.entities.RoleEnum;
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
public class RoleResponse {

    @Schema(name = "roleId", type = "Integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Número de identificação única da função")
    @JsonProperty("roleId")
    private Long id;

    @Schema(name = "name", enumAsRef = true,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Permissões de Usuário",
            example = "USER")
    private RoleEnum name;

    @Schema(name = "description", type = "String", minLength = 3,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Descrição da função")
    private String description;

    @Schema(name = "createdAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Criação")
    private OffsetDateTime createdAt;

    @Schema(name = "updatedAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Atualização")
    private OffsetDateTime updatedAt;

}
