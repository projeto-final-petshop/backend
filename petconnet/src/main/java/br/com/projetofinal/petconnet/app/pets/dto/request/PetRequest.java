package br.com.projetofinal.petconnet.app.pets.dto.request;

import br.com.projetofinal.petconnet.app.pets.entity.enums.PetSexEnum;
import br.com.projetofinal.petconnet.app.pets.entity.enums.PetSizeEnum;
import br.com.projetofinal.petconnet.app.pets.entity.enums.PetSpeciesEnum;
import br.com.projetofinal.petconnet.app.pets.entity.enums.PetTrainingEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = "Nome do animal de estimação é obrigatório")
    private String name;

    @NotBlank(message = "Sexo do animal de estimação é obrigatório")
    private PetSexEnum sex;

    @NotBlank(message = "Tamanho do animal de estimação é obrigatório")
    private PetSizeEnum size;

    @NotBlank(message = "Espécie do animal de estimação é obrigatório")
    private PetSpeciesEnum species;

    private PetTrainingEnum training;

//    private PetDetailsRequest details;

//    private OwnerRequest owner;

}
