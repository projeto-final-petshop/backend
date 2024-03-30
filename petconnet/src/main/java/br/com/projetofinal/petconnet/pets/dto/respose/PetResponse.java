package br.com.projetofinal.petconnet.pets.dto.respose;

import br.com.projetofinal.petconnet.owner.OwnerResponse;
import br.com.projetofinal.petconnet.pets.entity.PetDetails;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSexEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSizeEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSpeciesEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetTrainingEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    private Long petId;
    private String name;
    private PetSexEnum sex;
    private PetSizeEnum size;
    private PetSpeciesEnum species;
    private PetTrainingEnum training;
//    private PetDetails details;
//    private OwnerResponse owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
