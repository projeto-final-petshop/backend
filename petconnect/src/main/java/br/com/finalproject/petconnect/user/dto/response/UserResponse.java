package br.com.finalproject.petconnect.user.dto.response;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("userId")
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;
    private boolean active;
    private RoleResponse role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updatedAt;

}
