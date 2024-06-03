package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    @JsonProperty("petId")
    private Long id;

    private String name;

    private int age;

    private String color;

    private String breed;

    private String animalType;

    @Past(message = "A data de nascimento do pet deve estar no passado.")
    private LocalDate birthdate;

    @JsonProperty("userId")
    private Long userId;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
