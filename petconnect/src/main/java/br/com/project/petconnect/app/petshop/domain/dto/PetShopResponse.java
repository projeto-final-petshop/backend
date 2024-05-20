package br.com.project.petconnect.app.petshop.domain.dto;

import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetShopResponse {

    private Long id;
    private String businessName;
    private String cnpj;
    private String phoneNumber;
    private String email;
    private String address;
    private String services;
    private String businessHours;
    private Set<PetResponse> pets;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
