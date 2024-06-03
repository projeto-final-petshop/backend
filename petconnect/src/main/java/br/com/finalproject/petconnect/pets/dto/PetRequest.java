package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @Size(min = 3, max = 50, message = "O nome do pet deve ter entre {min} e {max} caracteres.")
    private String name;

    @PositiveOrZero(message = "A idade do pet deve ser um número positivo ou zero.")
    private int age;

    private String color;

    private String breed;

    private String animalType;

    @Past(message = "A data de nascimento do pet deve estar no passado.")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

}
