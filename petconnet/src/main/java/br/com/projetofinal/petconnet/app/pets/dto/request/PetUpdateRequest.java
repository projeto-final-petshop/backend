package br.com.projetofinal.petconnet.app.pets.dto.request;

import br.com.projetofinal.petconnet.app.pets.entity.AnimalType;
import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetUpdateRequest {

    private Long id;
    private String name;
    private String breed;
    private String color;
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private AnimalType animalType;

}
