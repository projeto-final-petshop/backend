package br.com.projetofinal.petconnet.pets.dto;

import br.com.projetofinal.petconnet.pets.entity.Owner;
import br.com.projetofinal.petconnet.pets.entity.PetDetails;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSexEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSizeEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSpeciesEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetTrainingEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {
    private String name;
    private PetSexEnum sex;
    private PetSizeEnum size;
    private PetSpeciesEnum species;
    private PetTrainingEnum training;
    private PetDetails details;
    private Owner owner;
}
