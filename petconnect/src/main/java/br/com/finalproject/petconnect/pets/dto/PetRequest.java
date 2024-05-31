package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Schema(name = "name", type = "String",
            minLength = 3, maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Animal de Estimação",
            example = "Luna")
    @Size(min = 3, max = 50, message = "O nome do pet deve ter entre 3 e 50 caracteres.")
    private String name;

    @Schema(name = "age", type = "int", format = "int32",
            minimum = "0", description = "Idade do Animal de Estimação",
            example = "2")
    @PositiveOrZero(message = "A idade do pet deve ser um número positivo ou zero.")
    private int age;

    @Schema(name = "color", type = "String",
            description = "Cor do Animal de Estimação",
            example = "Preto")
    private String color;

    @Schema(name = "breed", type = "String",
            description = "Raça do Animal de Estimação",
            example = "Siamese")
    private String breed;

    @Schema(name = "animalType", type = "String",
            description = "Tipo de Animal de Estimação",
            example = "CAT")
    private String animalType;

    @Schema(name = "birthdate", type = "string",
            format = "date", pattern = "dd/MM/yyyy",
            description = "Data de nascimento do Pet",
            example = "10/01/2022")
    @Past(message = "A data de nascimento do pet deve estar no passado.")
    @JsonFormat(pattern = "dd/MM/yyyy",
            shape = JsonFormat.Shape.STRING)
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

}
