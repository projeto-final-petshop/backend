package br.com.project.petconnect.app.owner.domain.dto;

import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponse {

    private Long id;

    private String name;

    private String cpf;

    private String phoneNumber;

    private String email;

    private String address;

    private Set<PetResponse> petSet = new HashSet<>();

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
