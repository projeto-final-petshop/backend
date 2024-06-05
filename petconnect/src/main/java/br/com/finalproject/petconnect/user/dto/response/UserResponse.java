package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record UserResponse(Long userId,
                           @Schema(
                                   description = "O nome do usuário",
                                   example = "João da Silva",
                                   minLength = 3,
                                   maxLength = 250,
                                   requiredMode = Schema.RequiredMode.REQUIRED,
                                   accessMode = Schema.AccessMode.READ_WRITE,
                                   type = "string"
                           )
                           String name,
                           @Schema(
                                   description = "Endereço de email do usuário.",
                                   example = "usario@dominio.com",
                                   pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
                                   accessMode = Schema.AccessMode.READ_WRITE,
                                   type = "string",
                                   format = "email",
                                   requiredMode = Schema.RequiredMode.REQUIRED
                           )
                           String email,
                           String cpf,
                           String phoneNumber,
                           boolean active,
                           RoleResponse role,
                           OffsetDateTime createdAt,
                           OffsetDateTime updatedAt) {
}
