package br.com.finalproject.petconnect.user.dto;

import br.com.finalproject.petconnect.pets.dto.PetResponse;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

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
    private String phoneNumber;
    private boolean active;
    private List<PetResponse> pets;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
