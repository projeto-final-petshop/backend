package br.com.projetofinal.petconnet.app.pets.dto.request;

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


//    private PetDetailsRequest details;

//    private OwnerRequest owner;

}
