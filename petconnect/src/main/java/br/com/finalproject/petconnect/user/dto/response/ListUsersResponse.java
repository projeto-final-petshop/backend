package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersResponse {

    @JsonProperty("userId")
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;
    private boolean active;
    private RoleResponse role;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
