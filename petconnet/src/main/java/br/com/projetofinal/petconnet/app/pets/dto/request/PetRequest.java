package br.com.projetofinal.petconnet.app.pets.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotBlank
    @Size(min = 3)
    private String name;

    private String breed;

    private String color;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private String animalType;

//    private AnimalType animalType;

    private Long userId;

}
