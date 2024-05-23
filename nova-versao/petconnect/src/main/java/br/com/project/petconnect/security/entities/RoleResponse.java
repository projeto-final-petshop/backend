package br.com.project.petconnect.security.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "RoleResponse", description = "Parâmetros necessários para retornar as informações da Role")
public class RoleResponse implements Serializable {

    private Long id;
    private RoleEnum name;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
