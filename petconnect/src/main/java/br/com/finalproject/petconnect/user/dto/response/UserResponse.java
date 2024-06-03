package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;

import java.time.OffsetDateTime;

public record UserResponse(Long userId,
                           String name,
                           String email,
                           String cpf,
                           String phoneNumber,
                           boolean active,
                           RoleResponse role,
                           OffsetDateTime createdAt,
                           OffsetDateTime updatedAt) {
}
