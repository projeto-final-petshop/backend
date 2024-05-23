package br.com.project.petconnect.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PetRequest", description = "Parâmetros necessários para Criar Pet")
public class PetRequest {

    private String name;

    @PositiveOrZero
    private int age;

    private String color;

    private String breed;

    private String animalType;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthdate;

    private Long userId;

}
