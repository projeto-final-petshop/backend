package br.com.project.petconnect.app.user.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Classe responsável por retornar as informações do usuário
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "created_at")
    private OffsetDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private OffsetDateTime updatedAt;

    private String role;

}
