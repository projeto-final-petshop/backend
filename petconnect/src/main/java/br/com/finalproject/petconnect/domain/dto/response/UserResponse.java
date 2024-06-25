package br.com.finalproject.petconnect.domain.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private Boolean active;
    private String address;
    private String cpf;
    private String email;
    private String name;
    private String phoneNumber;
    private RoleResponse role;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
