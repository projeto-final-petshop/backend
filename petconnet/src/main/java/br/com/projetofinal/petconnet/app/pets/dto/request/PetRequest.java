package br.com.projetofinal.petconnet.app.pets.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotBlank
    @Size(min = 3)
    private String name;

    @PositiveOrZero
    private Integer age;

    private String breed;

    private String color;

    @Size(min = 3)
    private String animalType;

}
